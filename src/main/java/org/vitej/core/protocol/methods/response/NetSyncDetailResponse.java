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

        /**
         * Return start height
         *
         * @return Start height
         */
        public Long getFrom() {
            return from;
        }

        public void setFrom(Long from) {
            this.from = from;
        }

        /**
         * Return target height
         *
         * @return Target height
         */
        public Long getTo() {
            return to;
        }

        public void setTo(Long to) {
            this.to = to;
        }

        /**
         * Return current snapshot chain height
         *
         * @return Current snapshot chain height
         */
        public Long getCurrent() {
            return current;
        }

        public void setCurrent(Long current) {
            this.current = current;
        }

        /**
         * Return synchron status description
         *
         * @return Synchron status description
         */
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Return download tasks
         *
         * @return Download tasks
         */
        public List<String> getTasks() {
            return tasks;
        }

        public void setTasks(List<String> tasks) {
            this.tasks = tasks;
        }

        /**
         * Return network connections to download ledger chunks
         *
         * @return Network connections to download ledger chunks
         */
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

        /**
         * Return ip address,
         *
         * @return Ip address
         */
        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * Return sync speed
         *
         * @return Sync speed
         */
        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        /**
         * Return sync task
         *
         * @return Sync task
         */
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
