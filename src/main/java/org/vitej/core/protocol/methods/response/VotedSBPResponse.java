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
         * Return name of SBP
         *
         * @return Name of SBP
         */
        public String getBlockProducerName() {
            return blockProducerName;
        }

        public void setBlockProducerName(String blockProducerName) {
            this.blockProducerName = blockProducerName;
        }

        /**
         * Return whether the SBP node is valid
         *
         * @return true for valid SBP node, false for cancelled SBP node
         */
        public Boolean isSBPActive() {
            return status == 1;
        }

        /**
         * Return status of registration
         *
         * @return Status of registration. 1 for valid SBP node, 2 for cancelled SBP node
         */
        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        /**
         * Return number of votes
         *
         * @return Number of votes, equivalent to account balance
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
