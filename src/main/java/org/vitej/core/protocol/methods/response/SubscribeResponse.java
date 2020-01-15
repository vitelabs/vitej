package org.vitej.core.protocol.methods.response;

public class SubscribeResponse extends Response<String> {
    public String getSubscriptionId() {
        return getResult();
    }
}
