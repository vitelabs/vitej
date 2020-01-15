package org.vitej.core.protocol;

import io.reactivex.Flowable;
import org.vitej.core.protocol.methods.request.Request;
import org.vitej.core.protocol.methods.response.Response;
import org.vitej.core.protocol.websocket.events.Notification;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface RpcService {
    <T extends Response> T send(Request request, Class<T> responseType) throws IOException;

    <T extends Response> CompletableFuture<T> sendAsync(Request request, Class<T> responseType);

    <T extends Notification<?>> Flowable<T> subscribe(Request request);

    void close() throws IOException;
}
