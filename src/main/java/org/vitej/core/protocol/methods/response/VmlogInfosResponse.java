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
import org.vitej.core.protocol.methods.VmLogInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VmlogInfosResponse extends Response<List<VmLogInfo>> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = VmlogInfosResponse.ResponseDeserialiser.class)
    public void setResult(List<VmLogInfo> result) {
        super.setResult(result);
    }

    public static class ResponseDeserialiser extends JsonDeserializer<List<VmLogInfo>> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public List<VmLogInfo> deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            List<VmLogInfo> vmlogs = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<VmLogInfo> vmlogIterator =
                        objectReader.readValues(jsonParser, VmLogInfo.class);
                while (vmlogIterator.hasNext()) {
                    vmlogs.add(vmlogIterator.next());
                }
            }
            return vmlogs;
        }
    }
}
