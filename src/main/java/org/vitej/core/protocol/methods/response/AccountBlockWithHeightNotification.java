package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.websocket.events.Notification;
import org.vitej.core.utils.NumericUtils;

import java.util.List;

public class AccountBlockWithHeightNotification extends Notification<List<AccountBlockWithHeightNotification.Result>> {
    public static class Result {
        private String hash;
        private String height;
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

        public Long getHeight() {
            return NumericUtils.stringToLong(height);
        }

        public String getHeightRaw() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public Boolean getRemoved() {
            return removed;
        }

        public void setRemoved(Boolean removed) {
            this.removed = removed;
        }
    }
}
