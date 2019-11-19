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
import org.vitej.core.protocol.methods.Response;
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

    public static class Result {
        private String totalStakeAmount;
        private Integer totalStakeCount;
        private List<StakeInfo> stakeList;

        public BigInteger getTotalStakeAmount() {
            return NumericUtils.stringToBigInteger(totalStakeAmount);
        }

        public String getTotalStakeAmountRaw() {
            return totalStakeAmount;
        }

        public void setTotalStakeAmount(String totalStakeAmount) {
            this.totalStakeAmount = totalStakeAmount;
        }

        public Integer getTotalStakeCount() {
            return totalStakeCount;
        }

        public void setTotalStakeCount(Integer totalStakeCount) {
            this.totalStakeCount = totalStakeCount;
        }

        public List<StakeInfo> getStakeList() {
            return stakeList;
        }

        public void setStakeList(List<StakeInfo> stakeList) {
            this.stakeList = stakeList;
        }
    }

    public static class StakeInfo {
        private String stakeAmount;
        private String beneficiary;
        private String expirationHeight;
        private Long expirationTime;
        private Boolean isDelegated;
        private String delegateAddress;
        private String stakeAddress;
        private Integer bid;
        private String id;

        public BigInteger getStakeAmount() {
            return NumericUtils.stringToBigInteger(stakeAmount);
        }

        public String getStakeAmountRaw() {
            return stakeAmount;
        }

        public void setStakeAmount(String stakeAmount) {
            this.stakeAmount = stakeAmount;
        }

        public Address getBeneficiary() {
            return Address.stringToAddress(beneficiary);
        }

        public String getBeneficiaryRaw() {
            return beneficiary;
        }

        public void setBeneficiary(String beneficiary) {
            this.beneficiary = beneficiary;
        }

        public Long getExpirationHeight() {
            return NumericUtils.stringToLong(expirationHeight);
        }

        public String getExpirationHeightRaw() {
            return expirationHeight;
        }

        public void setExpirationHeight(String expirationHeight) {
            this.expirationHeight = expirationHeight;
        }

        public Long getExpirationTime() {
            return expirationTime;
        }

        public void setExpirationTime(Long expirationTime) {
            this.expirationTime = expirationTime;
        }

        public Boolean getDelegated() {
            return isDelegated;
        }

        public void setDelegated(Boolean delegated) {
            isDelegated = delegated;
        }

        public Address getDelegateAddress() {
            return Address.stringToAddress(delegateAddress);
        }

        public String getDelegateAddressRaw() {
            return delegateAddress;
        }

        public void setDelegateAddress(String delegateAddress) {
            this.delegateAddress = delegateAddress;
        }

        public Address getStakeAddress() {
            return Address.stringToAddress(stakeAddress);
        }

        public String getStakeAddressRaw() {
            return stakeAddress;
        }

        public void setStakeAddress(String stakeAddress) {
            this.stakeAddress = stakeAddress;
        }

        public Integer getBid() {
            return bid;
        }

        public void setBid(Integer bid) {
            this.bid = bid;
        }

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
