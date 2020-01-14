package org.vitej.core.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Flowable;
import org.vitej.core.protocol.methods.request.Request;
import org.vitej.core.protocol.methods.response.Response;
import org.vitej.core.protocol.websocket.events.Notification;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public abstract class Service implements RpcService {
    protected final ObjectMapper objectMapper;

    public Service() {
        objectMapper = ProtocolHelper.getObjectMapper();
    }

    protected abstract InputStream performIO(String payload) throws IOException;

    @Override
    public <T extends Response> T send(Request request, Class<T> responseType) throws IOException {
        String payload = objectMapper.writeValueAsString(request);

        try (InputStream result = performIO(payload)) {
            if (result != null) {
                return objectMapper.readValue(result, responseType);
            } else {
                return null;
            }
        }
    }

    @Override
    public <T extends Response> CompletableFuture<T> sendAsync(
            Request jsonRpc20Request, Class<T> responseType) {
        return Async.run(() -> send(jsonRpc20Request, responseType));
    }

    @Override
    public <T extends Notification<?>> Flowable<T> subscribe(
            Request request) {
        throw new UnsupportedOperationException(
                String.format(
                        "Service %s does not support subscriptions",
                        this.getClass().getSimpleName()));
    }
}
