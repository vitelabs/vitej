package org.vitej.core.protocol.methods;

import org.vitej.core.utils.BytesUtils;

public class ContractInfo {
    private String code;
    private Integer responseLatency;
    private Integer randomDegree;
    private Integer quotaMultiplier;

    public byte[] getCode() {
        return BytesUtils.base64ToBytes(code);
    }

    public String getCodeRaw() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getResponseLatency() {
        return responseLatency;
    }

    public void setResponseLatency(Integer responseLatency) {
        this.responseLatency = responseLatency;
    }

    public Integer getRandomDegree() {
        return randomDegree;
    }

    public void setRandomDegree(Integer randomDegree) {
        this.randomDegree = randomDegree;
    }

    public Integer getQuotaMultiplier() {
        return quotaMultiplier;
    }

    public void setQuotaMultiplier(Integer quotaMultiplier) {
        this.quotaMultiplier = quotaMultiplier;
    }
}
