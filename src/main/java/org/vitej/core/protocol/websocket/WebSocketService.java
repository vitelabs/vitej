package org.vitej.core.protocol.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.subjects.BehaviorSubject;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vitej.core.protocol.ProtocolHelper;
import org.vitej.core.protocol.RpcService;
import org.vitej.core.protocol.methods.request.Request;
import org.vitej.core.protocol.methods.response.Response;
import org.vitej.core.protocol.methods.response.SubscribeResponse;
import org.vitej.core.protocol.websocket.events.Notification;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class WebSocketService implements RpcService {
    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    public static final String DEFAULT_URL = "ws://127.0.0.1:41420";
    private final String url;
    private OkHttpClient client;
    private WebSocket ws = null;

    private final ObjectMapper objectMapper = ProtocolHelper.getObjectMapper();
    private Map<Long, WebSocketRequest<?>> requestForId = new ConcurrentHashMap<>();
    private Map<Long, WebSocketSubscription<?>> subscriptionRequestForId =
            new ConcurrentHashMap<>();
    private Map<String, WebSocketSubscription<?>> subscriptionForId = new ConcurrentHashMap<>();

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    static final long REQUEST_TIMEOUT = 60;

    public WebSocketService() {
        this(DEFAULT_URL);
    }

    public WebSocketService(String url) {
        this(url, ProtocolHelper.getClient());
    }

    public WebSocketService(String url, OkHttpClient httpClient) {
        this.url = url;
        this.client = httpClient;
    }

    public void connect() {
        connect(s -> {
        }, t -> {
        }, () -> {
        });
    }

    public void connect(Consumer<String> onMessage, Consumer<Throwable> onError, Runnable onClose) {
        close();
        ws = client.newWebSocket(new okhttp3.Request.Builder().url(url).build(),
                new WebSocketListener() {
                    @Override
                    public void onMessage(WebSocket webSocket, String text) {
                        try {
                            onWebSocketMessage(text);
                        } catch (IOException e) {
                            log.error("Failed to deal with WebSocket message, {}", text, e);
                            throw new RuntimeException("Failed to deal with WebSocket message", e);
                        }
                        onMessage.accept(text);
                    }

                    @Override
                    public void onClosed(WebSocket webSocket, int code, String reason) {
                        log.warn("WebSocket closed {} {}", code, reason);
                        onClose.run();
                    }

                    @Override
                    public void onFailure(WebSocket webSocket, Throwable t, @Nullable okhttp3.Response response) {
                        log.error("WebSocket error", t);
                        onError.accept(t);
                    }
                });
    }

    public <T extends Response> T send(Request request, Class<T> responseType) throws IOException {
        try {
            return sendAsync(request, responseType).get();
        } catch (InterruptedException e) {
            Thread.interrupted();
            throw new IOException("Interrupted WebSocket request", e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            }
            throw new RuntimeException("Unexpected exception", e.getCause());
        }
    }

    public <T extends Response> CompletableFuture<T> sendAsync(
            Request request, Class<T> responseType) {
        CompletableFuture<T> result = new CompletableFuture<>();
        long requestId = request.getId();
        requestForId.put(requestId, new WebSocketRequest<>(result, responseType));
        try {
            String payload = objectMapper.writeValueAsString(request);
            ws.send(payload);

            executor.schedule(
                    () -> closeRequest(requestId, new IOException(String.format("Request %d timed out", requestId))),
                    REQUEST_TIMEOUT, TimeUnit.SECONDS);
        } catch (IOException e) {
            closeRequest(requestId, e);
        }

        return result;
    }

    public <T extends Notification<?>> Flowable<T> subscribe(Request request) {
        BehaviorSubject<T> subject = BehaviorSubject.create();
        subscriptionRequestForId.put(request.getId(), new WebSocketSubscription<>(subject, request.getResponseType()));
        try {
            send(request, SubscribeResponse.class);
        } catch (IOException e) {
            log.error("Failed to subscribe to RPC events with request id {}", request.getId());
            subject.onError(e);
        }

        return subject.doOnDispose(() -> {
            String subscriptionId = getSubscriptionId(subject);
            if (subscriptionId != null) {
                subscriptionForId.remove(subscriptionId);
            }
        }).toFlowable(BackpressureStrategy.BUFFER);
    }

    public void close() {
        if (ws != null) {
            try {
                ws.close(1000, "WebSocket closed");
            } catch (Exception e) {
                log.error("Close WebSocket failed", e);
            }
        }
    }

    private void onWebSocketMessage(String messageStr) throws IOException {
        try {
            JsonNode replyJson = objectMapper.readTree(messageStr);
            if (isReply(replyJson)) {
                long replyId = getReplyId(replyJson);
                WebSocketRequest request = requestForId.remove(replyId);
                if (request == null) {
                    log.error("Received reply for unexpected request id: {}", replyId);
                    return;
                }
                try {
                    Object reply = objectMapper.convertValue(replyJson, request.getResponseType());
                    if (reply instanceof SubscribeResponse) {
                        WebSocketSubscription subscription = subscriptionRequestForId.get(replyId);
                        SubscribeResponse subscriptionReply = (SubscribeResponse) reply;
                        if (!subscriptionReply.hasError()) {
                            subscriptionForId.put(subscriptionReply.getSubscriptionId(), new WebSocketSubscription<>(subscription.getSubject(), subscription.getResponseType()));
                        } else {
                            Response.Error error = subscriptionReply.getError();
                            log.error("Subscription request returned error: {}", error.getMessage());
                            subscription.getSubject().onError(new IOException(String.format("Subscription request failed with error: %s", error.getMessage())));
                        }
                    }
                    request.getOnReply().complete(reply);
                } catch (IllegalArgumentException e) {
                    request.getOnReply().completeExceptionally(new IOException(String.format("Failed to parse '%s' as type %s", messageStr, request.getResponseType()), e));
                }
            } else if (isSubscriptionEvent(replyJson)) {
                String subscriptionId = extractSubscriptionId(replyJson);
                WebSocketSubscription subscription = subscriptionForId.get(subscriptionId);
                if (subscription != null) {
                    Object event = objectMapper.convertValue(replyJson, subscription.getResponseType());
                    subscription.getSubject().onNext(event);
                } else {
                    log.warn("No subscriber for WebSocket event with subscription id {}", subscriptionId);
                }
            } else {
                throw new IOException("Unknown message type");
            }
        } catch (IOException e) {
            throw new IOException("failed to parse WebSocket message", e);
        }
    }

    private void closeRequest(long requestId, Exception e) {
        if (requestForId.containsKey(requestId)) {
            CompletableFuture result = requestForId.get(requestId).getOnReply();
            requestForId.remove(requestId);
            result.completeExceptionally(e);
        }
    }

    private <T extends Notification<?>> String getSubscriptionId(BehaviorSubject<T> subject) {
        return subscriptionForId.entrySet().stream()
                .filter(entry -> entry.getValue().getSubject() == subject)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    private String extractSubscriptionId(JsonNode replyJson) {
        return replyJson.get("params").get("subscription").asText();
    }

    private boolean isSubscriptionEvent(JsonNode replyJson) {
        return replyJson.has("method");
    }

    private boolean isReply(JsonNode replyJson) {
        return replyJson.has("id");
    }

    private long getReplyId(JsonNode replyJson) throws IOException {
        JsonNode idField = replyJson.get("id");
        if (idField == null) {
            throw new IOException("'id' field is missing in the reply");
        }

        if (!idField.isIntegralNumber()) {
            throw new IOException(
                    String.format("'id' expected to be long, but it is: '%s'", idField.asText()));
        }

        return idField.longValue();
    }
}
