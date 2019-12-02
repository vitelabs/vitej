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

    /**
     * 待提取奖励明细
     */
    public static class Result {
        private String blockProducingReward;
        private String votingReward;
        private String totalReward;
        private Boolean allRewardWithdrawed;

        /**
         * 获取待提取按块奖励
         *
         * @return 待提取按块奖励
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
         * 获取待提取按票奖励
         *
         * @return 待提取按票奖励
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
         * 获取待提取奖励
         *
         * @return 待提取奖励
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
         * 判断这个节点是否已取消，并且奖励已经都提取完了
         *
         * @return 值为true时表示节点已取消，并且所有的奖励已提取完
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
