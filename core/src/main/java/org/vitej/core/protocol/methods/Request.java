package org.vitej.core.protocol.methods;

import org.vitej.core.protocol.RpcService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Request<S, T extends Response> {
    private static AtomicLong nextId = new AtomicLong(0);

    private String jsonrpc = "2.0";
    private long id;
    private String method;
    private List<S> params;

    private RpcService rpcService;

    private Class<T> responseType;

    public Request(String method, List<S> params, RpcService rpcService, Class<T> responseType) {
        this.method = method;
        this.params = params;
        this.rpcService = rpcService;
        this.responseType = responseType;
        this.id = nextId.getAndIncrement();
    }

    public T send() throws IOException {
        return rpcService.send(this, responseType);
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<S> getParams() {
        return params;
    }

    public void setParams(List<S> params) {
        this.params = params;
    }
}
