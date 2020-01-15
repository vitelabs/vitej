package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.websocket.events.Notification;

import java.util.List;

public class VmlogNotification extends Notification<List<VmlogNotification.Result>> {
    public static class Result {
        private Vmlog vmlog;
        private String accountBlockHash;
        private String accountBlockHeight;
        private String address;
        private Boolean removed;

        public Vmlog getVmlog() {
            return vmlog;
        }

        public void setVmlog(Vmlog vmlog) {
            this.vmlog = vmlog;
        }

        public Hash getAccountBlockHash() {
            return Hash.stringToHash(accountBlockHash);
        }

        public String getAccountBlockHashRaw() {
            return accountBlockHash;
        }

        public void setAccountBlockHash(String accountBlockHash) {
            this.accountBlockHash = accountBlockHash;
        }

        public Long getAccountBlockHeight() {
            return Long.valueOf(accountBlockHeight);
        }

        public String getAccountBlockHeightRaw() {
            return accountBlockHeight;
        }

        public void setAccountBlockHeight(String accountBlockHeight) {
            this.accountBlockHeight = accountBlockHeight;
        }

        public Address getAddress() {
            return Address.stringToAddress(address);
        }

        public String getAddressRaw() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Boolean getRemoved() {
            return removed;
        }

        public void setRemoved(Boolean removed) {
            this.removed = removed;
        }
    }
}
