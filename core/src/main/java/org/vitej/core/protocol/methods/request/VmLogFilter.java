package org.vitej.core.protocol.methods.request;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VmLogFilter {
    private Map<String, Range> addressHeightRange = new HashMap<>();
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

    public void setAddressHeightRange(Map<String, Range> addressHeightRange) {
        this.addressHeightRange = addressHeightRange;
    }

    public List<List<Hash>> getTopics() {
        return topics;
    }

    public void setTopics(List<List<Hash>> topics) {
        this.topics = topics;
    }

    public static class Range {
        private String fromHeight = "0";
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

        public void setFromHeight(String fromHeight) {
            this.fromHeight = fromHeight;
        }

        public String getToHeight() {
            return toHeight;
        }

        public void setToHeight(String toHeight) {
            this.toHeight = toHeight;
        }
    }
}
