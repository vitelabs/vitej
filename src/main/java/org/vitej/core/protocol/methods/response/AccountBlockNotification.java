package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.websocket.events.Notification;

import java.util.List;

public class AccountBlockNotification extends Notification<List<AccountBlockNotification.Result>> {
    public static class Result {
        private String hash;
        private Boolean removed;

        public Hash getHash() {
            return Hash.stringToHash(hash);
        }

        public String getHashRaw() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public Boolean getRemoved() {
            return removed;
        }

        public void setRemoved(Boolean removed) {
            this.removed = removed;
        }
    }
}
