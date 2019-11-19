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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TokenInfoListResponse extends Response<List<TokenInfo>> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = TokenInfoListResponse.ResponseDeserialiser.class)
    public void setResult(List<TokenInfo> result) {
        super.setResult(result);
    }

    public static class ResponseDeserialiser extends JsonDeserializer<List<TokenInfo>> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public List<TokenInfo> deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            List<TokenInfo> tokenInfoList = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<TokenInfo> tokenInfoIterator =
                        objectReader.readValues(jsonParser, TokenInfo.class);
                while (tokenInfoIterator.hasNext()) {
                    tokenInfoList.add(tokenInfoIterator.next());
                }
            }
            return tokenInfoList;
        }
    }
}
