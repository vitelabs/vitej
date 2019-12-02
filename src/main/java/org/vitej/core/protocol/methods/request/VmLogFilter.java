package org.vitej.core.protocol.methods.request;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * vmlog查询筛选参数
 */
public class VmLogFilter {
    /**
     * 查询的合约地址和高度范围，格式为：合约地址-{起始高度，结束高度}
     */
    private Map<String, Range> addressHeightRange = new HashMap<>();
    /**
     * 查询的vmlog索引范围，第一个list表示第几个topic，第二个list表示对应topic的取值范围
     * 例如，[[],[A],[B,C]]表示查询的vmlog需要同时满足以下几个条件：
     * 至少有3个topic；
     * 其中第一个topic取值不限；
     * 第二个topic值为A；
     * 第三个topic值为B或C。
     */
    private List<List<Hash>> topics = new ArrayList<>();

    public VmLogFilter(Address address) {
        addressHeightRange.put(address.toString(), new Range());
    }

    public VmLogFilter(Address address, Long fromHeight, Long toHeight) {
        addressHeightRange.put(address.toString(), new Range(fromHeight, toHeight));
    }

    public Map<String, Range> getAddressHeightRange() {
        return addressHeightRange;
    }

    public VmLogFilter setAddressHeightRange(Map<String, Range> addressHeightRange) {
        this.addressHeightRange = addressHeightRange;
        return this;
    }

    public List<List<String>> getTopics() {
        List<List<String>> result = new ArrayList<>();
        if (topics != null) {
            for (List<Hash> l : topics) {
                List<String> r = new ArrayList<>();
                if (l != null) {
                    for (Hash h : l) {
                        r.add(h == null ? "" : h.toString());
                    }
                }
                result.add(r);
            }
        }
        return result;
    }

    public VmLogFilter setTopics(List<List<Hash>> topics) {
        this.topics = topics;
        return this;
    }

    /**
     * 高度范围
     */
    public static class Range {
        /**
         * 查询起始高度，默认从账户链上第一个块开始查询
         */
        private String fromHeight = "0";
        /**
         * 查询结束高度，默认查询到账户链上最新的块
         */
        private String toHeight = "0";

        public Range() {
        }

        public Range(Long fromHeight, Long toHeight) {
            this.fromHeight = fromHeight.toString();
            this.toHeight = toHeight.toString();
        }

        public String getFromHeight() {
            return fromHeight;
        }

        public Range setFromHeight(Long fromHeight) {
            this.fromHeight = fromHeight.toString();
            return this;
        }

        public String getToHeight() {
            return toHeight;
        }

        public Range setToHeight(Long toHeight) {
            this.toHeight = toHeight.toString();
            return this;
        }
    }
}
