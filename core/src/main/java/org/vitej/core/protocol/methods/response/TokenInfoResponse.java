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
import org.vitej.core.protocol.methods.TokenInfo;

import java.io.IOException;

public class TokenInfoResponse extends Response<TokenInfo> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = TokenInfoResponse.ResponseDeserialiser.class)
    public void setResult(TokenInfo result) {
        super.setResult(result);
    }

    public static class ResponseDeserialiser extends JsonDeserializer<TokenInfo> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public TokenInfo deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, TokenInfo.class);
            } else {
                return null;
            }
        }
    }
}
