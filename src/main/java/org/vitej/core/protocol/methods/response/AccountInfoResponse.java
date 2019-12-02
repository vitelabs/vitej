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
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.utils.NumericUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class AccountInfoResponse extends Response<AccountInfoResponse.Result> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = AccountInfoResponse.ResponseDeserialiser.class)
    public void setResult(AccountInfoResponse.Result result) {
        super.setResult(result);
    }

    public static class Result {
        private String address;
        private String blockCount;
        private Map<String, BalanceInfo> balanceInfoMap;

        /**
         * 获取账户地址
         *
         * @return 账户地址
         */
        public Address getAddress() {
            return Address.stringToAddress(address);
        }

        public String getAddressRaw() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * 获取账户链上的账户块数量
         *
         * @return 账户链上的账户块数量
         */
        public Long getBlockCount() {
            return NumericUtils.stringToLong(blockCount);
        }

        public String getBlockCountRaw() {
            return blockCount;
        }

        public void setBlockCount(String blockCount) {
            this.blockCount = blockCount;
        }

        /**
         * 获取账户余额
         *
         * @return 账户余额
         */
        public Map<TokenId, BalanceInfo> getBalanceInfoMap() {
            Map<TokenId, BalanceInfo> map = new HashMap<>(balanceInfoMap.size());
            balanceInfoMap.forEach((key, value) -> {
                map.put(new TokenId(key), value);
            });
            return map;
        }

        public Map<String, BalanceInfo> getBalanceInfoMapRaw() {
            return balanceInfoMap;
        }

        public void setBalanceInfoMap(Map<String, BalanceInfo> balanceInfoMap) {
            this.balanceInfoMap = balanceInfoMap;
        }
    }

    public static class BalanceInfo {
        private TokenInfo tokenInfo;
        private String balance;
        private String transactionCount;

        /**
         * 获取代币信息
         *
         * @return 代币信息
         */
        public TokenInfo getTokenInfo() {
            return tokenInfo;
        }

        public void setTokenInfo(TokenInfo tokenInfo) {
            this.tokenInfo = tokenInfo;
        }

        /**
         * 获取代币余额
         *
         * @return 代币余额
         */
        public BigInteger getBalance() {
            return NumericUtils.stringToBigInteger(balance);
        }

        public String getBalanceRaw() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        /**
         * 获取账户链上和该币种相关的账户块数量
         *
         * @return 账户链上和该币种相关的账户块数量
         */
        public Long getTransactionCount() {
            return NumericUtils.stringToLong(transactionCount);
        }

        public String getTransactionCountRaw() {
            return transactionCount;
        }

        public void setTransactionCount(String transactionCount) {
            this.transactionCount = transactionCount;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<AccountInfoResponse.Result> {
        private ObjectReader objectReader = ProtocolHelper.getObjectReader();

        @Override
        public AccountInfoResponse.Result deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, AccountInfoResponse.Result.class);
            } else {
                return null;
            }
        }
    }
}
