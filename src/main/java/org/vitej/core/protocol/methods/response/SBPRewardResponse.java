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

        /**
         * Return un-retrieved block creation rewards
         *
         * @return Un-retrieved block creation rewards
         */
        public BigInteger getBlockProducingReward() {
            return NumericUtils.stringToBigInteger(blockProducingReward);
        }

        public String getBlockProducingRewardRaw() {
            return blockProducingReward;
        }

        public void setBlockProducingReward(String blockProducingReward) {
            this.blockProducingReward = blockProducingReward;
        }

        /**
         * Return un-retrieved candidate additional rewards(voting rewards)
         *
         * @return Un-retrieved candidate additional rewards(voting rewards)
         */
        public BigInteger getVotingReward() {
            return NumericUtils.stringToBigInteger(votingReward);
        }

        public String getVotingRewardRaw() {
            return votingReward;
        }

        public void setVotingReward(String votingReward) {
            this.votingReward = votingReward;
        }

        /**
         * Return the total rewards that have not been retrieved
         *
         * @return The total rewards that have not been retrieved
         */
        public BigInteger getTotalReward() {
            return NumericUtils.stringToBigInteger(totalReward);
        }

        public String getTotalRewardRaw() {
            return totalReward;
        }

        public void setTotalReward(String totalReward) {
            this.totalReward = totalReward;
        }

        /**
         * Return whether the SBP node has been cancelled and all rewards have been withdrawn
         *
         * @return If true , the SBP node has been cancelled and all rewards have been withdrawn
         */
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
