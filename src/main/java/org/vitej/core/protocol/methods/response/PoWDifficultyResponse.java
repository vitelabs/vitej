package org.vitej.core.protocol.methods.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.vitej.core.protocol.ProtocolHelper;
import org.vitej.core.utils.NumericUtils;

import java.io.IOException;
import java.math.BigInteger;

public class PoWDifficultyResponse extends Response<PoWDifficultyResponse.Result> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = PoWDifficultyResponse.ResponseDeserialiser.class)
    public void setResult(PoWDifficultyResponse.Result result) {
        super.setResult(result);
    }

    public static class Result {
        private String requiredQuota;
        private String difficulty;
        private String qc;
        private Boolean isCongestion;

        /**
         * 获取交易需要的配额
         *
         * @return 交易需要的配额
         */
        public Long getRequiredQuota() {
            return NumericUtils.stringToLong(requiredQuota);
        }

        public String getRequiredQuotaRaw() {
            return requiredQuota;
        }

        public void setRequiredQuota(String requiredQuota) {
            this.requiredQuota = requiredQuota;
        }

        /**
         * 获取需要计算的PoW难度
         *
         * @return 需要计算的PoW难度，如果为空字符串，说明不需要计算PoW
         */
        public BigInteger getDifficulty() {
            return NumericUtils.stringToBigInteger(difficulty);
        }

        public String getDifficultyRaw() {
            return difficulty;
        }

        public void setDifficulty(String difficulty) {
            this.difficulty = difficulty;
        }

        /**
         * 获取拥堵系数
         *
         * @return 拥堵系数，单位：1e18，例如，返回2e18表示当前拥堵程度为预期的2倍
         */
        public BigInteger getQc() {
            return NumericUtils.stringToBigInteger(qc);
        }

        public String getQcRaw() {
            return qc;
        }

        public void setQc(String qc) {
            this.qc = qc;
        }

        /**
         * 判断全网是否拥堵
         *
         * @return 全网是否拥堵，true 表示当前全网拥堵，此时配额成本提高，false 表示不拥堵
         */
        public Boolean getCongestion() {
            return isCongestion;
        }

        public void setCongestion(Boolean congestion) {
            isCongestion = congestion;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<PoWDifficultyResponse.Result> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public PoWDifficultyResponse.Result deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, PoWDifficultyResponse.Result.class);
            } else {
                return null;
            }
        }
    }
}
