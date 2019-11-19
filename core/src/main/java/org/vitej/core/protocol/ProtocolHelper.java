package org.vitej.core.protocol;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public class ProtocolHelper {
    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

    static {
        DEFAULT_OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        DEFAULT_OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getObjectMapper() {
        return DEFAULT_OBJECT_MAPPER;
    }

    public static ObjectReader getObjectReader() {
        return DEFAULT_OBJECT_MAPPER.reader();
    }
}
