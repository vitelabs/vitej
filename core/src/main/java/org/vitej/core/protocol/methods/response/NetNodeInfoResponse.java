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

public class NetNodeInfoResponse extends Response<NetNodeInfoResponse.Result> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = NetNodeInfoResponse.ResponseDeserialiser.class)
    public void setResult(NetNodeInfoResponse.Result result) {
        super.setResult(result);
    }

    public static class Result {
        private String id;
        private String name;
        private Integer netId;
        private Integer version;
        private String address;
        private Integer peerCount;
        private Long height;
        private Integer nodes;
        private Float broadCheckFailedRatio;
        private List<NetSyncDetailResponse.SyncConnectionStatus> connections;
        private List<PeerInfo> peers;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getNetId() {
            return netId;
        }

        public void setNetId(Integer netId) {
            this.netId = netId;
        }

        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getPeerCount() {
            return peerCount;
        }

        public void setPeerCount(Integer peerCount) {
            this.peerCount = peerCount;
        }

        public Long getHeight() {
            return height;
        }

        public void setHeight(Long height) {
            this.height = height;
        }

        public Integer getNodes() {
            return nodes;
        }

        public void setNodes(Integer nodes) {
            this.nodes = nodes;
        }

        public Float getBroadCheckFailedRatio() {
            return broadCheckFailedRatio;
        }

        public void setBroadCheckFailedRatio(Float broadCheckFailedRatio) {
            this.broadCheckFailedRatio = broadCheckFailedRatio;
        }

        public List<NetSyncDetailResponse.SyncConnectionStatus> getConnections() {
            return connections;
        }

        public void setConnections(List<NetSyncDetailResponse.SyncConnectionStatus> connections) {
            this.connections = connections;
        }

        public List<PeerInfo> getPeers() {
            return peers;
        }

        public void setPeers(List<PeerInfo> peers) {
            this.peers = peers;
        }
    }

    public static class PeerInfo {
        private String id;
        private String name;
        private Long version;
        private Long height;
        private String address;
        private Integer flag;
        private Boolean superior;
        private Boolean reliable;
        private String createAt;
        private Integer readQueue;
        private Integer writeQueue;
        private List<String> peers;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getVersion() {
            return version;
        }

        public void setVersion(Long version) {
            this.version = version;
        }

        public Long getHeight() {
            return height;
        }

        public void setHeight(Long height) {
            this.height = height;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getFlag() {
            return flag;
        }

        public void setFlag(Integer flag) {
            this.flag = flag;
        }

        public Boolean getSuperior() {
            return superior;
        }

        public void setSuperior(Boolean superior) {
            this.superior = superior;
        }

        public Boolean getReliable() {
            return reliable;
        }

        public void setReliable(Boolean reliable) {
            this.reliable = reliable;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public Integer getReadQueue() {
            return readQueue;
        }

        public void setReadQueue(Integer readQueue) {
            this.readQueue = readQueue;
        }

        public Integer getWriteQueue() {
            return writeQueue;
        }

        public void setWriteQueue(Integer writeQueue) {
            this.writeQueue = writeQueue;
        }

        public List<String> getPeers() {
            return peers;
        }

        public void setPeers(List<String> peers) {
            this.peers = peers;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<NetNodeInfoResponse.Result> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public NetNodeInfoResponse.Result deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, NetNodeInfoResponse.Result.class);
            } else {
                return null;
            }
        }
    }
}
