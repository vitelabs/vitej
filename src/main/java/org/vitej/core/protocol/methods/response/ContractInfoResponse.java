package org.vitej.core.protocol.methods.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.vitej.core.protocol.ProtocolHelper;
import org.vitej.core.protocol.methods.ContractInfo;
import org.vitej.core.protocol.methods.Response;

import java.io.IOException;

public class ContractInfoResponse extends Response<ContractInfo> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = ContractInfoResponse.ResponseDeserialiser.class)
    public void setResult(ContractInfo result) {
        super.setResult(result);
    }

    public static class ResponseDeserialiser extends JsonDeserializer<ContractInfo> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public ContractInfo deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, ContractInfo.class);
            } else {
                return null;
            }
        }
    }
}
