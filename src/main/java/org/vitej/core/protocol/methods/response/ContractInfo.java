package org.vitej.core.protocol.methods.response;

import org.vitej.core.utils.BytesUtils;

/**
 * Contract information
 *
 * @see <a href="https://mainnet.vite.wiki/zh/tutorial/contract/contract.html">https://mainnet.vite.wiki/zh/tutorial/contract/contract.html</a>
 */
public class ContractInfo {
    private String code;
    private Integer responseLatency;
    private Integer randomDegree;
    private Integer quotaMultiplier;

    /**
     * Return code of contract
     *
     * @return Code of contract
     */
    public byte[] getCode() {
        return BytesUtils.base64ToBytes(code);
    }

    public String getCodeRaw() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Return response latency
     *
     * @return Response latency
     */
    public Integer getResponseLatency() {
        return responseLatency;
    }

    public void setResponseLatency(Integer responseLatency) {
        this.responseLatency = responseLatency;
    }

    /**
     * Return random degree
     *
     * @return Random degree
     */
    public Integer getRandomDegree() {
        return randomDegree;
    }

    public void setRandomDegree(Integer randomDegree) {
        this.randomDegree = randomDegree;
    }

    /**
     * Return quota multiplier
     *
     * @return Quota multiplier
     */
    public Integer getQuotaMultiplier() {
        return quotaMultiplier;
    }

    public void setQuotaMultiplier(Integer quotaMultiplier) {
        this.quotaMultiplier = quotaMultiplier;
    }
}
