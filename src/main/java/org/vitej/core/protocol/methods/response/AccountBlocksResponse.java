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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountBlocksResponse extends Response<List<AccountBlock>> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = AccountBlocksResponse.ResponseDeserialiser.class)
    public void setResult(List<AccountBlock> result) {
        super.setResult(result);
    }

    public static class ResponseDeserialiser extends JsonDeserializer<List<AccountBlock>> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public List<AccountBlock> deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            List<AccountBlock> accountBlocks = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<AccountBlock> accountBlockIterator =
                        objectReader.readValues(jsonParser, AccountBlock.class);
                while (accountBlockIterator.hasNext()) {
                    accountBlocks.add(accountBlockIterator.next());
                }
            }
            return accountBlocks;
        }
    }
}
