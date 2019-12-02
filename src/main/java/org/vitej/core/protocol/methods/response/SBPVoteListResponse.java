package org.vitej.core.protocol.methods.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.vitej.core.protocol.ProtocolHelper;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.utils.NumericUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SBPVoteListResponse extends Response<List<SBPVoteListResponse.Result>> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = SBPVoteListResponse.ResponseDeserialiser.class)
    public void setResult(List<Result> result) {
        super.setResult(result);
    }

    /**
     * 超姐节点获得的投票数
     */
    public static class Result {
        private String sbpName;
        private String blockProducingAddress;
        private String votes;

        /**
         * 获取超级节点名称
         *
         * @return 超级节点名称
         */
        public String getSbpName() {
            return sbpName;
        }

        public void setSbpName(String sbpName) {
            this.sbpName = sbpName;
        }

        /**
         * 获取当前出块地址
         *
         * @return 当前出块地址
         */
        public Address getBlockProducingAddress() {
            return Address.stringToAddress(blockProducingAddress);
        }

        public String getBlockProducingAddressRaw() {
            return blockProducingAddress;
        }

        public void setBlockProducingAddress(String blockProducingAddress) {
            this.blockProducingAddress = blockProducingAddress;
        }

        /**
         * 获取当前获得的总投票数
         *
         * @return 当前获得的总投票数
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

    public static class ResponseDeserialiser extends JsonDeserializer<List<Result>> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public List<Result> deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            List<Result> sbpList = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<Result> sbpIterator =
                        objectReader.readValues(jsonParser, Result.class);
                while (sbpIterator.hasNext()) {
                    sbpList.add(sbpIterator.next());
                }
            }
            return sbpList;
        }
    }
}
