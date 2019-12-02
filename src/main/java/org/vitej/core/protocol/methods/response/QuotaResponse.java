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

public class QuotaResponse extends Response<QuotaResponse.Result> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = QuotaResponse.ResponseDeserialiser.class)
    public void setResult(QuotaResponse.Result result) {
        super.setResult(result);
    }

    public static class Result {
        private String currentQuota;
        private String maxQuota;
        private String stakeAmount;

        /**
         * 获取当前可用配额
         *
         * @return 当前可用配额
         */
        public Long getCurrentQuota() {
            return NumericUtils.stringToLong(currentQuota);
        }

        public String getCurrentQuotaRaw() {
            return currentQuota;
        }

        public void setCurrentQuota(String currentQuota) {
            this.currentQuota = currentQuota;
        }

        /**
         * 获取最大可用配额，即utpe对应的配额
         *
         * @return 最大可用配额
         */
        public Long getMaxQuota() {
            return NumericUtils.stringToLong(maxQuota);
        }

        public String getMaxQuotaRaw() {
            return maxQuota;
        }

        public void setMaxQuota(String maxQuota) {
            this.maxQuota = maxQuota;
        }

        /**
         * 获取抵押金额
         *
         * @return 抵押金额
         */
        public BigInteger getStakeAmount() {
            return NumericUtils.stringToBigInteger(stakeAmount);
        }

        public String getStakeAmountRaw() {
            return stakeAmount;
        }

        public void setStakeAmount(String stakeAmount) {
            this.stakeAmount = stakeAmount;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<QuotaResponse.Result> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public QuotaResponse.Result deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, QuotaResponse.Result.class);
            } else {
                return null;
            }
        }
    }
}
