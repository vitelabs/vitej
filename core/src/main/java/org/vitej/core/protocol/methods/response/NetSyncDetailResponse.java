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

import java.io.IOException;
import java.util.List;

public class NetSyncDetailResponse extends Response<NetSyncDetailResponse.Result> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = NetSyncDetailResponse.ResponseDeserialiser.class)
    public void setResult(NetSyncDetailResponse.Result result) {
        super.setResult(result);
    }

    public static class Result {
        private Long from;
        private Long to;
        private Long current;
        private String status;
        private List<String> tasks;
        private List<SyncConnectionStatus> connections;

        public Long getFrom() {
            return from;
        }

        public void setFrom(Long from) {
            this.from = from;
        }

        public Long getTo() {
            return to;
        }

        public void setTo(Long to) {
            this.to = to;
        }

        public Long getCurrent() {
            return current;
        }

        public void setCurrent(Long current) {
            this.current = current;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<String> getTasks() {
            return tasks;
        }

        public void setTasks(List<String> tasks) {
            this.tasks = tasks;
        }

        public List<SyncConnectionStatus> getConnections() {
            return connections;
        }

        public void setConnections(List<SyncConnectionStatus> connections) {
            this.connections = connections;
        }
    }

    public static class SyncConnectionStatus {
        private String address;
        private String speed;
        private String task;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getTask() {
            return task;
        }

        public void setTask(String task) {
            this.task = task;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<NetSyncDetailResponse.Result> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public NetSyncDetailResponse.Result deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, NetSyncDetailResponse.Result.class);
            } else {
                return null;
            }
        }
    }
}
