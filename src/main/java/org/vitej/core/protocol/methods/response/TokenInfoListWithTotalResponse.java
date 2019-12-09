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
import java.util.List;

public class TokenInfoListWithTotalResponse extends Response<TokenInfoListWithTotalResponse.Result> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = TokenInfoListWithTotalResponse.ResponseDeserialiser.class)
    public void setResult(Result result) {
        super.setResult(result);
    }

    public static class Result {
        private Integer totalCount;
        private List<TokenInfo> tokenInfoList;

        /**
         * Return the total number of tokens
         *
         * @return The total number of tokens
         */
        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        /**
         * Return token information list
         *
         * @return Token information list
         */
        public List<TokenInfo> getTokenInfoList() {
            return tokenInfoList;
        }

        public void setTokenInfoList(List<TokenInfo> tokenInfoList) {
            this.tokenInfoList = tokenInfoList;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<Result> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public Result deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, Result.class);
            } else {
                return null;
            }
        }
    }
}
