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
        private Integer peerCount;
        private Long height;
        private Float broadCheckFailedRatio;
        private List<PeerInfo> peers;

        /**
         * 获取本节点的NodeID
         *
         * @return 本节点的NodeID
         */
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /**
         * 获取本节点的名称，通过 node_config.json 中的 Identity 字段设置
         *
         * @return 本节点的名称
         */
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * 获取本节点的网络Id
         *
         * @return 本节点的网络Id
         */
        public Integer getNetId() {
            return netId;
        }

        public void setNetId(Integer netId) {
            this.netId = netId;
        }

        /**
         * 获取本节点所连接的peer数量
         *
         * @return 本节点所连接的peer数量
         */
        public Integer getPeerCount() {
            return peerCount;
        }

        public void setPeerCount(Integer peerCount) {
            this.peerCount = peerCount;
        }

        /**
         * 获取当前快照链的高度
         *
         * @return 当前快照链的高度
         */
        public Long getHeight() {
            return height;
        }

        public void setHeight(Long height) {
            this.height = height;
        }

        /**
         * 获取节点广播失败率
         *
         * @return 节点广播失败率
         */
        public Float getBroadCheckFailedRatio() {
            return broadCheckFailedRatio;
        }

        public void setBroadCheckFailedRatio(Float broadCheckFailedRatio) {
            this.broadCheckFailedRatio = broadCheckFailedRatio;
        }

        /**
         * 获取连接的peer信息
         *
         * @return 连接的peer信息
         */
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
        private Long height;
        private String address;
        private String createAt;
        private List<String> peers;

        /**
         * 获取节点的NodeID
         *
         * @return 节点的NodeID
         */
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /**
         * 获取节点的名称
         *
         * @return 节点的名称
         */
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * 获取节点的当前快照链的高度
         *
         * @return 节点的当前快照链的高度
         */
        public Long getHeight() {
            return height;
        }

        public void setHeight(Long height) {
            this.height = height;
        }

        /**
         * 获取节点的地址信息
         *
         * @return 节点的地址信息
         */
        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * 获取节点的接入时间
         *
         * @return 节点的接入时间（北京时间），例如"2019-11-26 11:33:02"
         */
        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
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
