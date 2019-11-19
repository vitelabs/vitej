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
import org.vitej.core.protocol.methods.Vmlog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VmlogsResponse extends Response<List<Vmlog>> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = VmlogsResponse.ResponseDeserialiser.class)
    public void setResult(List<Vmlog> result) {
        super.setResult(result);
    }

    public static class ResponseDeserialiser extends JsonDeserializer<List<Vmlog>> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public List<Vmlog> deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            List<Vmlog> vmlogs = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<Vmlog> vmlogIterator =
                        objectReader.readValues(jsonParser, Vmlog.class);
                while (vmlogIterator.hasNext()) {
                    vmlogs.add(vmlogIterator.next());
                }
            }
            return vmlogs;
        }
    }
}
