package org.vitej.core.protocol.methods.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.vitej.core.protocol.ProtocolHelper;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.utils.NumericUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class StakeListResponse extends Response<StakeListResponse.Result> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = StakeListResponse.ResponseDeserialiser.class)
    public void setResult(StakeListResponse.Result result) {
        super.setResult(result);
    }

    /**
     * 抵押列表信息
     */
    public static class Result {
        private String totalStakeAmount;
        private Integer totalStakeCount;
        private List<StakeInfo> stakeList;

        /**
         * 获取抵押的总金额
         *
         * @return 抵押的总金额
         */
        public BigInteger getTotalStakeAmount() {
            return NumericUtils.stringToBigInteger(totalStakeAmount);
        }

        public String getTotalStakeAmountRaw() {
            return totalStakeAmount;
        }

        public void setTotalStakeAmount(String totalStakeAmount) {
            this.totalStakeAmount = totalStakeAmount;
        }

        /**
         * 获取抵押信息总条数
         *
         * @return 抵押信息总条数
         */
        public Integer getTotalStakeCount() {
            return totalStakeCount;
        }

        public void setTotalStakeCount(Integer totalStakeCount) {
            this.totalStakeCount = totalStakeCount;
        }

        /**
         * 获取抵押信息列表
         *
         * @return 抵押信息列表
         */
        public List<StakeInfo> getStakeList() {
            return stakeList;
        }

        public void setStakeList(List<StakeInfo> stakeList) {
            this.stakeList = stakeList;
        }
    }

    /**
     * 抵押信息明细
     */
    public static class StakeInfo {
        private String stakeAmount;
        private String beneficiary;
        private String expirationHeight;
        private Long expirationTime;
        private String stakeAddress;
        private String id;

        /**
         * 获取抵押金额
         *
         * @return 抵押金额
         */
        public BigInteger getStakeAmount() {
            return NumericUtils.stringToBigInteger(stakeAmount);
        }

        public String getStakeAmountRaw() {
            return stakeAmount;
        }

        public void setStakeAmount(String stakeAmount) {
            this.stakeAmount = stakeAmount;
        }

        /**
         * 获取配额受益地址
         *
         * @return 配额受益地址
         */
        public Address getBeneficiary() {
            return Address.stringToAddress(beneficiary);
        }

        public String getBeneficiaryRaw() {
            return beneficiary;
        }

        public void setBeneficiary(String beneficiary) {
            this.beneficiary = beneficiary;
        }

        /**
         * 获取到期快照块高度，到期后可以取回抵押
         *
         * @return 到期快照块高度
         */
        public Long getExpirationHeight() {
            return NumericUtils.stringToLong(expirationHeight);
        }

        public String getExpirationHeightRaw() {
            return expirationHeight;
        }

        public void setExpirationHeight(String expirationHeight) {
            this.expirationHeight = expirationHeight;
        }

        /**
         * 获取预估到期时间，注意如果抵押未到期，则预估到期时间会随出块率而变化
         *
         * @return 预估到期时间
         */
        public Long getExpirationTime() {
            return expirationTime;
        }

        public void setExpirationTime(Long expirationTime) {
            this.expirationTime = expirationTime;
        }


        /**
         * 获取抵押地址
         *
         * @return 抵押地址
         */
        public Address getStakeAddress() {
            return Address.stringToAddress(stakeAddress);
        }

        public String getStakeAddressRaw() {
            return stakeAddress;
        }

        public void setStakeAddress(String stakeAddress) {
            this.stakeAddress = stakeAddress;
        }

        /**
         * 获取抵押id，即抵押请求交易hash
         *
         * @return 抵押id
         */
        public Hash getId() {
            return Hash.stringToHash(id);
        }

        public String getIdRaw() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<StakeListResponse.Result> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public StakeListResponse.Result deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, StakeListResponse.Result.class);
            } else {
                return null;
            }
        }
    }
}
