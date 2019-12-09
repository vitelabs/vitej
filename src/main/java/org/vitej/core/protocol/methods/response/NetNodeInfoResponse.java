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
        private List<PeerInfo> peers;

        /**
         * Return node id
         *
         * @return Node id
         */
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /**
         * Return node name, configured in Identity field of node_config.json
         *
         * @return Node name
         */
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * Return the ID of Vite network connected
         *
         * @return The ID of Vite network connected
         */
        public Integer getNetId() {
            return netId;
        }

        public void setNetId(Integer netId) {
            this.netId = netId;
        }

        /**
         * Return number of peers connected
         *
         * @return Number of peers connected
         */
        public Integer getPeerCount() {
            return peerCount;
        }

        public void setPeerCount(Integer peerCount) {
            this.peerCount = peerCount;
        }

        /**
         * Return current snapshot chain height
         *
         * @return Current snapshot chain height
         */
        public Long getHeight() {
            return height;
        }

        public void setHeight(Long height) {
            this.height = height;
        }

        /**
         * Return information of peers connected
         *
         * @return Information of peers connected
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
         * Return node id
         *
         * @return Node id
         */
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /**
         * Return node name
         *
         * @return Node name
         */
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * Return current snapshot chain height
         *
         * @return Current snapshot chain height
         */
        public Long getHeight() {
            return height;
        }

        public void setHeight(Long height) {
            this.height = height;
        }

        /**
         * Return ip address
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
         * Return the time when this peer connected
         *
         * @return The time when this peer connected(Beijing time), for example "2019-11-26 11:33:02"
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
