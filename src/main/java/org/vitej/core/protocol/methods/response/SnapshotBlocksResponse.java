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

public class SnapshotBlocksResponse extends Response<List<SnapshotBlock>> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = SnapshotBlocksResponse.ResponseDeserialiser.class)
    public void setResult(List<SnapshotBlock> result) {
        super.setResult(result);
    }

    public static class ResponseDeserialiser extends JsonDeserializer<List<SnapshotBlock>> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public List<SnapshotBlock> deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            List<SnapshotBlock> snapshotBlocks = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<SnapshotBlock> snapshotBlockIterator =
                        objectReader.readValues(jsonParser, SnapshotBlock.class);
                while (snapshotBlockIterator.hasNext()) {
                    snapshotBlocks.add(snapshotBlockIterator.next());
                }
            }
            return snapshotBlocks;
        }
    }
}
