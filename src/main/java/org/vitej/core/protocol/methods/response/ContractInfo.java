package org.vitej.core.protocol.methods.response;

import org.vitej.core.utils.BytesUtils;

/**
 * 合约信息
 *
 * @see <a href="https://mainnet.vite.wiki/zh/tutorial/contract/contract.html">https://mainnet.vite.wiki/zh/tutorial/contract/contract.html</a>
 */
public class ContractInfo {
    private String code;
    private Integer responseLatency;
    private Integer randomDegree;
    private Integer quotaMultiplier;

    /**
     * 获取合约代码
     *
     * @return 合约代码
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
     * 获取确认数
     *
     * @return 确认数
     */
    public Integer getResponseLatency() {
        return responseLatency;
    }

    public void setResponseLatency(Integer responseLatency) {
        this.responseLatency = responseLatency;
    }

    /**
     * 获取随机数确认数
     *
     * @return 随机数确认数
     */
    public Integer getRandomDegree() {
        return randomDegree;
    }

    public void setRandomDegree(Integer randomDegree) {
        this.randomDegree = randomDegree;
    }

    /**
     * 获取配额翻倍数
     *
     * @return 配额翻倍数，单位：10，例如返回值为15表示调用合约请求交易收取1.5倍配额
     */
    public Integer getQuotaMultiplier() {
        return quotaMultiplier;
    }

    public void setQuotaMultiplier(Integer quotaMultiplier) {
        this.quotaMultiplier = quotaMultiplier;
    }
}
