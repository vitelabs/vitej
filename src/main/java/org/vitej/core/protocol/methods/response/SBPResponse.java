package org.vitej.core.protocol.methods.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.vitej.core.protocol.ProtocolHelper;

import java.io.IOException;

public class SBPResponse extends Response<SBPInfo> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = SBPResponse.ResponseDeserialiser.class)
    public void setResult(SBPInfo result) {
        super.setResult(result);
    }

    public static class ResponseDeserialiser extends JsonDeserializer<SBPInfo> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public SBPInfo deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, SBPInfo.class);
            } else {
                return null;
            }
        }
    }
}
