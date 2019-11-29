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
import java.util.Map;

public class SBPRewardDetailResponse extends Response<SBPRewardDetailResponse.Result> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = SBPRewardDetailResponse.ResponseDeserialiser.class)
    public void setResult(SBPRewardDetailResponse.Result result) {
        super.setResult(result);
    }

    public static class Result {
        private Map<String, RewardInfoDetail> rewardMap;
        private Long startTime;
        private Long endTime;
        private String cycle;

        public Map<String, RewardInfoDetail> getRewardMap() {
            return rewardMap;
        }

        public void setRewardMap(Map<String, RewardInfoDetail> rewardMap) {
            this.rewardMap = rewardMap;
        }

        public Long getStartTime() {
            return startTime;
        }

        public void setStartTime(Long startTime) {
            this.startTime = startTime;
        }

        public Long getEndTime() {
            return endTime;
        }

        public void setEndTime(Long endTime) {
            this.endTime = endTime;
        }

        public Long getCycle() {
            return NumericUtils.stringToLong(cycle);
        }

        public String getCycleRaw() {
            return cycle;
        }

        public void setCycle(String cycle) {
            this.cycle = cycle;
        }
    }

    public static class RewardInfoDetail {
        private String blockProducingReward;
        private String votingReward;
        private String totalReward;
        private String producedBlocks;
        private String targetBlocks;

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

        public Long getProducedBlocks() {
            return NumericUtils.stringToLong(producedBlocks);
        }

        public String getProducedBlocksRaw() {
            return producedBlocks;
        }

        public void setProducedBlocks(String producedBlocks) {
            this.producedBlocks = producedBlocks;
        }

        public Long getTargetBlocks() {
            return NumericUtils.stringToLong(targetBlocks);
        }

        public String getTargetBlocksRaw() {
            return targetBlocks;
        }

        public void setTargetBlocks(String targetBlocks) {
            this.targetBlocks = targetBlocks;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<SBPRewardDetailResponse.Result> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public SBPRewardDetailResponse.Result deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, SBPRewardDetailResponse.Result.class);
            } else {
                return null;
            }
        }
    }
}
