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

public class AccountBlockResponse extends Response<AccountBlock> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = AccountBlockResponse.ResponseDeserialiser.class)
    public void setResult(AccountBlock result) {
        super.setResult(result);
    }

    public static class ResponseDeserialiser extends JsonDeserializer<AccountBlock> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public AccountBlock deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, AccountBlock.class);
            } else {
                return null;
            }
        }
    }
}
