package org.vitej.core.protocol.methods.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.vitej.core.protocol.ProtocolHelper;
import org.vitej.core.protocol.methods.Response;
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

        public Long getRequiredQuota() {
            return NumericUtils.stringToLong(requiredQuota);
        }

        public String getRequiredQuotaRaw() {
            return requiredQuota;
        }

        public void setRequiredQuota(String requiredQuota) {
            this.requiredQuota = requiredQuota;
        }

        public BigInteger getDifficulty() {
            return NumericUtils.stringToBigInteger(difficulty);
        }

        public String getDifficultyRaw() {
            return difficulty;
        }

        public void setDifficulty(String difficulty) {
            this.difficulty = difficulty;
        }

        public BigInteger getQc() {
            return NumericUtils.stringToBigInteger(qc);
        }

        public String getQcRaw() {
            return qc;
        }

        public void setQc(String qc) {
            this.qc = qc;
        }

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
