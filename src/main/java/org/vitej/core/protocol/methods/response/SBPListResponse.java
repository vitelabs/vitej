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

public class SBPListResponse extends Response<List<SBPInfo>> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = SBPListResponse.ResponseDeserialiser.class)
    public void setResult(List<SBPInfo> result) {
        super.setResult(result);
    }

    public static class ResponseDeserialiser extends JsonDeserializer<List<SBPInfo>> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public List<SBPInfo> deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            List<SBPInfo> sbpList = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<SBPInfo> sbpIterator =
                        objectReader.readValues(jsonParser, SBPInfo.class);
                while (sbpIterator.hasNext()) {
                    sbpList.add(sbpIterator.next());
                }
            }
            return sbpList;
        }
    }
}
