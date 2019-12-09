package org.vitej.core.protocol.methods.request;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Event logs filter params.
 */
public class VmLogFilter {
    /**
     * Query logs of the specified contract account address with given range. At least one address
     * must be specified.
     */
    private Map<String, Range> addressHeightRange = new HashMap<>();
    /**
     * Prefix of topics
     * Topic examples：
     * {} matches all logs
     * {{A}} matches the logs having "A" as the first element
     * {{},{B}} matches the logs having "B" as the second element
     * {{A},{B}} matches the logs having "A" as the first element and "B" as the second element
     * {{A,B},{C,D}} matches the logs having "A" or "B" as the first element, and "C" or "D" as the
     * second element
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
     * height range
     */
    public static class Range {
        /**
         * Start height. 0 means starting from the first block
         */
        private String fromHeight = "0";
        /**
         * End height. 0 means stopping at the latest block
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
