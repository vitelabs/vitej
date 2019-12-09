package org.vitej.core.protocol.methods.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.vitej.core.protocol.ProtocolHelper;
import org.vitej.core.utils.NumericUtils;

import java.io.IOException;

public class NetSyncInfoResponse extends Response<NetSyncInfoResponse.Result> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = NetSyncInfoResponse.ResponseDeserialiser.class)
    public void setResult(NetSyncInfoResponse.Result result) {
        super.setResult(result);
    }

    public static class Result {
        private String from;
        private String to;
        private String current;
        private Integer state;
        private String status;

        /**
         * Return sync start height
         *
         * @return Sync start height
         */
        public Long getFrom() {
            return NumericUtils.stringToLong(from);
        }

        public String getFromRaw() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        /**
         * Return sync target height
         *
         * @return Sync target height
         */
        public Long getTo() {
            return NumericUtils.stringToLong(to);
        }

        public String getToRaw() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        /**
         * Return current snapshot chain height
         *
         * @return Current snapshot chain height
         */
        public Long getCurrent() {
            return NumericUtils.stringToLong(current);
        }

        public String getCurrentRaw() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        /**
         * Return sync state
         *
         * @return Sync state: 0 - not start, 1 - syncing, 2 - complete, 3 - error, 4 - cancelled
         */
        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        /**
         * Return sync state description
         *
         * @return Sync state description
         */
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<NetSyncInfoResponse.Result> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public NetSyncInfoResponse.Result deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, NetSyncInfoResponse.Result.class);
            } else {
                return null;
            }
        }
    }
}
