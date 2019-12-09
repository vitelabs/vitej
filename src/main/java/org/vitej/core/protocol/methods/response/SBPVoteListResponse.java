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
     * Current number of votes of a SBP node
     */
    public static class Result {
        private String sbpName;
        private String blockProducingAddress;
        private String votes;

        /**
         * Return name of SBP
         *
         * @return Name of SBP
         */
        public String getSbpName() {
            return sbpName;
        }

        public void setSbpName(String sbpName) {
            this.sbpName = sbpName;
        }

        /**
         * Return block producing addresss
         *
         * @return Block producing addresss
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
         * Return number of votes
         *
         * @return Number of votes
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
