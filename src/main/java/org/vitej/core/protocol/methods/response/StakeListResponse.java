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
     * Staking records
     */
    public static class Result {
        private String totalStakeAmount;
        private Integer totalStakeCount;
        private List<StakeInfo> stakeList;

        /**
         * Return the total staking amount of the account
         *
         * @return The total staking amount of the account
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
         * Return the total number of staking records
         *
         * @return The total number of staking records
         */
        public Integer getTotalStakeCount() {
            return totalStakeCount;
        }

        public void setTotalStakeCount(Integer totalStakeCount) {
            this.totalStakeCount = totalStakeCount;
        }

        /**
         * Return staking record list
         *
         * @return Staking record list
         */
        public List<StakeInfo> getStakeList() {
            return stakeList;
        }

        public void setStakeList(List<StakeInfo> stakeList) {
            this.stakeList = stakeList;
        }
    }

    /**
     * Staking record
     */
    public static class StakeInfo {
        private String stakeAmount;
        private String beneficiary;
        private String expirationHeight;
        private Long expirationTime;
        private String stakeAddress;
        private String id;

        /**
         * Return smount of staking
         *
         * @return Amount of staking
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
         * Return address of staking beneficiary
         *
         * @return Address of staking beneficiary
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
         * Return target unlocking height
         *
         * @return Target unlocking height
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
         * Return estimated target unlocking time
         *
         * @return Estimated target unlocking time
         */
        public Long getExpirationTime() {
            return expirationTime;
        }

        public void setExpirationTime(Long expirationTime) {
            this.expirationTime = expirationTime;
        }


        /**
         * Return address of staking account
         *
         * @return Address of staking account
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
         * Return staking record id
         *
         * @return Staking record id
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
