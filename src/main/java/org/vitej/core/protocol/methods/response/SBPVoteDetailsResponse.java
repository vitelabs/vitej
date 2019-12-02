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
import java.util.*;

public class SBPVoteDetailsResponse extends Response<List<SBPVoteDetailsResponse.Result>> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = SBPVoteDetailsResponse.ResponseDeserialiser.class)
    public void setResult(List<Result> result) {
        super.setResult(result);
    }

    /**
     * 周期内最后一轮共识的超级节点投票明细
     */
    public static class Result {
        private String blockProducerName;
        private String totalVotes;
        private String blockProducingAddress;
        private List<String> historyProducingAddresses;
        private Map<String, String> addressVoteMap;

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
         * 获取该超级节点在当天最后一轮共识结果中获得的总投票数
         *
         * @return 该超级节点在当天最后一轮共识结果中获得的总投票数
         */
        public BigInteger getTotalVotes() {
            return NumericUtils.stringToBigInteger(totalVotes);
        }

        public String getTotalVotesRaw() {
            return totalVotes;
        }

        public void setTotalVotes(String totalVotes) {
            this.totalVotes = totalVotes;
        }

        /**
         * 获取超级节点当前出块地址
         *
         * @return 超级节点当前出块地址
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
         * 获取超级节点历史使用过的所有的出块地址
         *
         * @return 超级节点历史使用过的所有的出块地址
         */
        public List<Address> getHistoryProducingAddresses() {
            List<Address> list = new ArrayList<>(historyProducingAddresses.size());
            historyProducingAddresses.forEach((value) -> {
                list.add(new Address(value));
            });
            return list;
        }

        public List<String> getHistoryProducingAddressesRaw() {
            return historyProducingAddresses;
        }

        public void setHistoryProducingAddresses(List<String> historyProducingAddresses) {
            this.historyProducingAddresses = historyProducingAddresses;
        }

        /**
         * 获取投票明细
         *
         * @return 投票地址-投票数，即账户余额
         */
        public Map<Address, BigInteger> getAddressVoteMap() {
            Map<Address, BigInteger> map = new HashMap<>(addressVoteMap.size());
            addressVoteMap.forEach((key, value) -> {
                map.put(new Address(key), NumericUtils.stringToBigInteger(value));
            });
            return map;
        }

        public Map<String, String> getAddressVoteMapRaw() {
            return addressVoteMap;
        }

        public void setAddressVoteMap(Map<String, String> addressVoteMap) {
            this.addressVoteMap = addressVoteMap;
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
