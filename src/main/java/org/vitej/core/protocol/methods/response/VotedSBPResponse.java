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

public class VotedSBPResponse extends Response<VotedSBPResponse.Result> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = VotedSBPResponse.ResponseDeserialiser.class)
    public void setResult(VotedSBPResponse.Result result) {
        super.setResult(result);
    }

    public static class Result {
        private String blockProducerName;
        private Integer status; // 1 - sbp active, 2 - sbp revoked
        private String votes;

        /**
         * 获取超级节点名称
         *
         * @return 超级节点名称
         */
        public String getBlockProducerName() {
            return blockProducerName;
        }

        public void setBlockProducerName(String blockProducerName) {
            this.blockProducerName = blockProducerName;
        }

        /**
         * 判断超级节点状态注册状态
         *
         * @return true-超级节点注册状态正常，false-超级节点已取消注册
         */
        public Boolean isSBPActive() {
            return status == 1;
        }

        /**
         * 获取超级节点注册状态
         *
         * @return 超级节点注册状态，1-正常，2-已取消注册
         */
        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        /**
         * 获取投票总数
         *
         * @return 投票总数，即投票账户余额之和
         */
        public BigInteger getVotes() {
            return NumericUtils.stringToBigInteger(votes);
        }

        public String getVotesRaw() {
            return votes;
        }

        public void setVotes(String votes) {
            this.votes = votes;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<VotedSBPResponse.Result> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public VotedSBPResponse.Result deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, VotedSBPResponse.Result.class);
            } else {
                return null;
            }
        }
    }
}
