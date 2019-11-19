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
import org.vitej.core.protocol.methods.Response;
import org.vitej.core.utils.NumericUtils;

import java.io.IOException;

public class UnreceivedTransactionSummaryResponse extends Response<UnreceivedTransactionSummaryResponse.Result> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = UnreceivedTransactionSummaryResponse.ResponseDeserialiser.class)
    public void setResult(UnreceivedTransactionSummaryResponse.Result result) {
        super.setResult(result);
    }

    public static class Result {
        private String address;
        private String blockCount;

        public Address getAddress() {
            return Address.stringToAddress(address);
        }

        public String getAddressRaw() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Long getBlockCount() {
            return NumericUtils.stringToLong(blockCount);
        }

        public String getBlockCountRaw() {
            return blockCount;
        }

        public void setBlockCount(String blockCount) {
            this.blockCount = blockCount;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<UnreceivedTransactionSummaryResponse.Result> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public UnreceivedTransactionSummaryResponse.Result deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, UnreceivedTransactionSummaryResponse.Result.class);
            } else {
                return null;
            }
        }
    }
}
