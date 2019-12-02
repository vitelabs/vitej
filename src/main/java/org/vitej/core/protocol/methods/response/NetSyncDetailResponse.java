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
         * 获取同步的起始高度
         *
         * @return 同步的起始高度
         */
        public Long getFrom() {
            return from;
        }

        public void setFrom(Long from) {
            this.from = from;
        }

        /**
         * 获取同步的目标高度
         *
         * @return 同步的目标高度
         */
        public Long getTo() {
            return to;
        }

        public void setTo(Long to) {
            this.to = to;
        }

        /**
         * 获取当前快照链的高度
         *
         * @return 当前快照链的高度
         */
        public Long getCurrent() {
            return current;
        }

        public void setCurrent(Long current) {
            this.current = current;
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

        /**
         * 获取同步任务队列
         *
         * @return 同步任务队列，例如"692001-693000 done"表示高度范围在692001-693000的快照块已经同步完成
         */
        public List<String> getTasks() {
            return tasks;
        }

        public void setTasks(List<String> tasks) {
            this.tasks = tasks;
        }

        /**
         * 获取用于同步账本的连接
         *
         * @return 用于同步账本的连接
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
         * 获取节点地址描述，例如"24a160122317e6e4940ef2a91242b07f@118.25.49.80:8484",
         *
         * @return 节点地址描述
         */
        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * 获取节点的同步速度
         *
         * @return 节点的同步速度，例如"0.00 Byte/s",
         */
        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        /**
         * 获取节点的同步任务
         *
         * @return 节点的同步任务
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
