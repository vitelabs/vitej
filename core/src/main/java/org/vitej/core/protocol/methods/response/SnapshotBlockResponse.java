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
import org.vitej.core.protocol.methods.SnapshotBlock;

import java.io.IOException;

public class SnapshotBlockResponse extends Response<SnapshotBlock> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = SnapshotBlockResponse.ResponseDeserialiser.class)
    public void setResult(SnapshotBlock result) {
        super.setResult(result);
    }

    public static class ResponseDeserialiser extends JsonDeserializer<SnapshotBlock> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public SnapshotBlock deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, SnapshotBlock.class);
            } else {
                return null;
            }
        }
    }
}
