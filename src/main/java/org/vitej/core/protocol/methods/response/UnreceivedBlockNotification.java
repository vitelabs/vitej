package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.websocket.events.Notification;

import java.util.List;

public class UnreceivedBlockNotification extends Notification<List<UnreceivedBlockNotification.Result>> {
    public static class Result {
        private String hash;
        private Boolean received;
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

        public Boolean getReceived() {
            return received;
        }

        public void setReceived(Boolean received) {
            this.received = received;
        }

        public Boolean getRemoved() {
            return removed;
        }

        public void setRemoved(Boolean removed) {
            this.removed = removed;
        }
    }
}
