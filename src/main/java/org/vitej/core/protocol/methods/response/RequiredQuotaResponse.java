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

public class RequiredQuotaResponse extends Response<RequiredQuotaResponse.Result> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = RequiredQuotaResponse.ResponseDeserialiser.class)
    public void setResult(RequiredQuotaResponse.Result result) {
        super.setResult(result);
    }

    /**
     * 获取交易需要的配额
     *
     * @return 交易需要的配额
     */
    public Long getRequiredQuota() {
        return getResult() == null ? null : NumericUtils.stringToLong(getResult().getRequiredQuota());
    }

    public static class Result {
        private String requiredQuota;

        public String getRequiredQuota() {
            return requiredQuota;
        }

        public void setRequiredQuota(String requiredQuota) {
            this.requiredQuota = requiredQuota;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<RequiredQuotaResponse.Result> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public RequiredQuotaResponse.Result deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, RequiredQuotaResponse.Result.class);
            } else {
                return null;
            }
        }
    }
}
