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
         * 获取同步的起始高度
         *
         * @return 同步的起始高度
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
         * 获取同步的目标高度
         *
         * @return 同步的目标高度
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
         * 获取当前快照链的高度
         *
         * @return 当前快照链的高度
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
         * 获取同步状态
         *
         * @return 同步状态：0 未开始同步，1 同步中，2 同步完成，3 同步出错，4 同步取消，5 同步数据已全部下载
         */
        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        /**
         * 获取同步状态的描述
         *
         * @return 同步状态的描述
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
