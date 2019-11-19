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

public class SBPRewardResponse extends Response<SBPRewardResponse.Result> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = SBPRewardResponse.ResponseDeserialiser.class)
    public void setResult(SBPRewardResponse.Result result) {
        super.setResult(result);
    }

    public static class Result {
        private String blockProducingReward;
        private String votingReward;
        private String totalReward;
        private Boolean allRewardWithdrawed;

        public BigInteger getBlockProducingReward() {
            return NumericUtils.stringToBigInteger(blockProducingReward);
        }

        public String getBlockProducingRewardRaw() {
            return blockProducingReward;
        }

        public void setBlockProducingReward(String blockProducingReward) {
            this.blockProducingReward = blockProducingReward;
        }

        public BigInteger getVotingReward() {
            return NumericUtils.stringToBigInteger(votingReward);
        }

        public String getVotingRewardRaw() {
            return votingReward;
        }

        public void setVotingReward(String votingReward) {
            this.votingReward = votingReward;
        }

        public BigInteger getTotalReward() {
            return NumericUtils.stringToBigInteger(totalReward);
        }

        public String getTotalRewardRaw() {
            return totalReward;
        }

        public void setTotalReward(String totalReward) {
            this.totalReward = totalReward;
        }

        public Boolean getAllRewardWithdrawed() {
            return allRewardWithdrawed;
        }

        public void setAllRewardWithdrawed(Boolean allRewardWithdrawed) {
            this.allRewardWithdrawed = allRewardWithdrawed;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<SBPRewardResponse.Result> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public SBPRewardResponse.Result deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, SBPRewardResponse.Result.class);
            } else {
                return null;
            }
        }
    }
}
