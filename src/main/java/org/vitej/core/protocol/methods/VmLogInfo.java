package org.vitej.core.protocol.methods;

public class VmLogInfo {
    private Vmlog vmlog;
    private String accountBlockHash;
    private String accountBlockHeight;
    private String address;

    public Vmlog getVmlog() {
        return vmlog;
    }

    public void setVmlog(Vmlog vmlog) {
        this.vmlog = vmlog;
    }

    public String getAccountBlockHash() {
        return accountBlockHash;
    }

    public void setAccountBlockHash(String accountBlockHash) {
        this.accountBlockHash = accountBlockHash;
    }

    public String getAccountBlockHeight() {
        return accountBlockHeight;
    }

    public void setAccountBlockHeight(String accountBlockHeight) {
        this.accountBlockHeight = accountBlockHeight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
