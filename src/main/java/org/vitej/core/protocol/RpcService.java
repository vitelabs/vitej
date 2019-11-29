package org.vitej.core.protocol;

import org.vitej.core.protocol.methods.Request;
import org.vitej.core.protocol.methods.Response;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface RpcService {
    <T extends Response> T send(Request request, Class<T> responseType) throws IOException;

    <T extends Response> CompletableFuture<T> sendAsync(Request request, Class<T> responseType);

    void close() throws IOException;
}
