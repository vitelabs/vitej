package org.vitej.core.utils.abi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.utils.BytesUtils;
import org.vitej.core.wallet.Crypto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;
import static java.lang.String.format;
import static org.apache.commons.lang3.ArrayUtils.subarray;
import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.stripEnd;

/**
 * Contract abi utils
 */
public class Abi extends ArrayList<Abi.Entry> {
    private final static ObjectMapper DEFAULT_MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

    /**
     * Parse json abi definition string into Abi instance.
     *
     * @param json Json abi definition, for example:
     *             "[{\"type\":\"function\",\"name\":\"VoteForSBP\",
     *             \"inputs\":[{\"name\":\"sbpName\",\"type\":\"string\"}]}]"
     * @return Abi instance
     */
    public static Abi fromJson(String json) {
        try {
            return DEFAULT_MAPPER.readValue(json, Abi.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encode function id and function params
     *
     * @param name Function name, for example: VoteForSBP
     * @param args Function params, for example: ViteSBP_01
     * @return Encoded function data, can be used in call contract request block data.
     */
    public byte[] encodeFunction(String name, Object... args) {
        Function f = findFunctionByName(name);
        if (f == null) {
            throw new RuntimeException("function not exist");
        }
        return f.encode(args);
    }

    /**
     * Decode function id and function params. Reverse of encodeFunction method
     *
     * @param encoded Encoded function data
     * @return Decoded function params
     */
    public List<?> decodeFunction(byte[] encoded) {
        Predicate<Function> p = (v1) -> Arrays.equals(v1.encodeSignature(), Function.extractSignature(encoded));
        Abi.Function f = find(Function.class, Abi.Entry.Type.function, p);
        if (f != null) {
            return f.decode(encoded);
        } else {
            return null;
        }
    }

    /**
     * Encode constructor params
     *
     * @param args Constructor method params
     * @return Encoded constructor method data, can be used to generate create contract
     * request block data
     */
    public byte[] encodeConstructor(Object... args) {
        Constructor c = find(Constructor.class, Entry.Type.constructor, object -> true);
        if (c != null) {
            return c.encode(args);
        }
        return null;
    }

    /**
     * Decode constructor method params. Reverse of encodeConstructor method
     *
     * @param encoded Encoded constructor method data
     * @return Encoded constructor method params
     */
    public List<?> decodeConstructor(byte[] encoded) {
        Constructor c = find(Constructor.class, Entry.Type.constructor, object -> true);
        if (c != null) {
            return Entry.Param.decodeList(c.inputs, encoded);
        }
        return null;
    }

    /**
     * Encode offchain function id and function params
     *
     * @param name Offchain function name
     * @param args Offchain function params
     * @return Encoded offchain function data, can be used to call offchain method
     */
    public byte[] encodeOffchain(String name, Object... args) {
        Offchain f = findOffchainByName(name);
        if (f == null) {
            throw new RuntimeException("offchain not exist");
        }
        return f.encode(args);
    }

    /**
     * Decode offchain function outputs
     *
     * @param name    Offchain function name
     * @param encoded Encoded offchain function output params. It's the return value of Vitej.callOffChainMethod
     * @return Decoded offchain function outputs
     */
    public List<?> decodeOffchainOutput(String name, byte[] encoded) {
        Predicate<Offchain> p = (v1) -> v1.name.equals(name);
        Abi.Offchain f = find(Offchain.class, Abi.Entry.Type.offchain, p);
        if (f != null) {
            return f.decodeOutput(encoded);
        } else {
            return null;
        }
    }

    /**
     * Decode view function outputs
     *
     * @param name    Function name. Must be a view or pure function.
     * @param encoded Encoded function's output params. It's the return value of Vitej.queryContractState
     * @return Decoded function outputs
     */
    public List<?> decodeFunctionOutput(String name, byte[] encoded) {
        Predicate<Function> p = (v1) -> v1.name.equals(name);
        Abi.Function f = find(Function.class, Entry.Type.function, p);
        if (f != null) {
            return f.decodeOutput(encoded);
        } else {
            return null;
        }
    }

    public List<?> decodeEvent(byte[] data, byte[][] topics) {
        if (topics == null || topics.length == 0) {
            return null;
        }
        Predicate<Event> p = (v1) -> Arrays.equals(v1.encodeSignature(), topics[0]);
        Abi.Event e = find(Event.class, Abi.Entry.Type.event, p);
        if (e != null) {
            List<Object> result = new ArrayList<>(e.inputs.size());
            byte[][] argTopics = e.anonymous ? topics : subarray(topics, 1, topics.length);
            List<?> indexed = Entry.Param.decodeList(e.filteredInputs(true), BytesUtils.merge(argTopics));
            List<?> notIndexed = Entry.Param.decodeList(e.filteredInputs(false), data);
            for (Entry.Param input : e.inputs) {
                result.add(input.indexed ? indexed.remove(0) : notIndexed.remove(0));
            }
            return result;
        } else {
            return null;
        }
    }

    /**
     * Decode vmlog
     *
     * @param data   Vmlog data to decode
     * @param topics Vmlog topics, the first element of topics is used as event id
     * @return Decoded vmlog input params
     */
    public List<?> decodeEvent(byte[] data, List<Hash> topics) {
        if (CollectionUtils.isEmpty(topics)) {
            return null;
        }
        return decodeEvent(data, convertTopics(topics));
    }

    private static byte[][] convertTopics(List<Hash> topics) {
        if (topics == null) {
            return null;
        }
        byte[][] topicsBytes = new byte[topics.size()][];
        for (int i = 0; i < topics.size(); i++) {
            topicsBytes[i] = topics.get(i).getBytes();
        }
        return topicsBytes;
    }

    private <T extends Abi.Entry> T find(Class<T> resultClass, final Abi.Entry.Type type, final Predicate<T> searchPredicate) {
        return (T) IterableUtils.find(this, entry -> entry.type == type && searchPredicate.evaluate((T) entry));
    }

    /**
     * Find function by encoded function id
     *
     * @param encoded Function id or call contract request block data
     * @return Function instance which matches the encoded data
     */
    public Function findFunctionByData(byte[] encoded) {
        Predicate<Function> p = (v1) -> Arrays.equals(v1.encodeSignature(), Function.extractSignature(encoded));
        Abi.Function f = find(Function.class, Abi.Entry.Type.function, p);
        if (f != null) {
            return f;
        } else {
            return null;
        }
    }

    /**
     * Find function by name
     *
     * @param name Function name
     * @return Function instance which matches the function name
     */
    public Function findFunctionByName(String name) {
        Predicate<Function> p = (v1) -> v1.name.equals(name);
        Abi.Function f = find(Function.class, Abi.Entry.Type.function, p);
        if (f != null) {
            return f;
        } else {
            return null;
        }
    }

    /**
     * Find offchain function by name
     *
     * @param name Offchain function name
     * @return Offchain function instance which matches the function name
     */
    public Offchain findOffchainByName(String name) {
        Predicate<Offchain> p = (v1) -> v1.name.equals(name);
        Abi.Offchain f = find(Offchain.class, Abi.Entry.Type.offchain, p);
        if (f != null) {
            return f;
        } else {
            return null;
        }
    }

    /**
     * Find vmlog by name
     *
     * @param name Vmlog name
     * @return Vmlog instance which matches the name
     */
    public Event findEventByName(String name) {
        Predicate<Event> p = (v1) -> v1.name.equals(name);
        Abi.Event e = find(Event.class, Entry.Type.event, p);
        if (e != null) {
            return e;
        } else {
            return null;
        }
    }

    /**
     * Find vmlog by topics
     *
     * @param topics The first element is used as event id
     * @return Vmlog instance which matches the topics
     */
    public Event findEventByTopics(List<Hash> topics) {
        return findEventByTopics(convertTopics(topics));
    }

    public Event findEventByTopics(byte[][] topics) {
        if (topics == null || topics.length == 0) {
            return null;
        }
        Predicate<Event> p = (v1) -> Arrays.equals(v1.encodeSignature(), topics[0]);
        Abi.Event e = find(Event.class, Entry.Type.event, p);
        if (e != null) {
            return e;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @JsonInclude(Include.NON_NULL)
    public static abstract class Entry {
        public enum Type {
            constructor,
            function,
            event,
            fallback,
            offchain,
            message
        }

        @JsonInclude(Include.NON_NULL)
        public static class Param {
            public Boolean indexed = false;
            public String name;
            public SolidityType type;

            public static List<?> decodeList(List<Param> params, byte[] encoded) {
                List<Object> result = new ArrayList<>(params.size());

                int offset = 0;
                for (Param param : params) {
                    Object decoded = param.type.isDynamicType()
                            ? param.type.decode(encoded, SolidityType.IntType.decodeInt(encoded, offset).intValue())
                            : param.type.decode(encoded, offset);
                    result.add(decoded);

                    offset += param.type.getFixedSize();
                }

                return result;
            }

            @Override
            public String toString() {
                return format("%s%s%s", type.getCanonicalName(), (indexed != null && indexed) ? " indexed " : " ", name);
            }
        }

        public final Boolean anonymous;
        public final String name;
        public final List<Param> inputs;
        public final List<Param> outputs;
        public final Type type;
        public final Boolean payable;


        public Entry(Boolean anonymous, String name, List<Param> inputs, List<Param> outputs, Type type, Boolean payable) {
            this.anonymous = anonymous;
            this.name = name;
            this.inputs = inputs;
            this.outputs = outputs;
            this.type = type;
            this.payable = payable;
        }

        public String formatSignature() {
            StringBuilder paramsTypes = new StringBuilder();
            for (Entry.Param param : inputs) {
                paramsTypes.append(param.type.getCanonicalName()).append(",");
            }

            return format("%s(%s)", name, stripEnd(paramsTypes.toString(), ","));
        }

        public byte[] fingerprintSignature() {
            return Crypto.digest(formatSignature().getBytes());
        }

        public byte[] encodeSignature() {
            return fingerprintSignature();
        }

        @JsonCreator
        public static Entry create(@JsonProperty("anonymous") boolean anonymous,
                                   @JsonProperty("name") String name,
                                   @JsonProperty("inputs") List<Param> inputs,
                                   @JsonProperty("outputs") List<Param> outputs,
                                   @JsonProperty("type") Type type,
                                   @JsonProperty(value = "payable", required = false, defaultValue = "false") Boolean payable) {
            Entry result = null;
            switch (type) {
                case constructor:
                    result = new Constructor(inputs);
                    break;
                case function:
                case message:
                case fallback:
                    result = new Function(name, inputs, outputs, payable, type);
                    break;
                case event:
                    result = new Event(anonymous, name, inputs);
                    break;
                case offchain:
                    result = new Offchain(name, inputs, outputs, payable);
                    break;
            }

            return result;
        }

        public byte[] encodeArguments(Object... args) {
            if (args.length > inputs.size())
                throw new RuntimeException("Too many arguments: " + args.length + " > " + inputs.size());

            int staticSize = 0;
            int dynamicCnt = 0;
            // calculating static size and number of dynamic params
            for (int i = 0; i < args.length; i++) {
                SolidityType type = inputs.get(i).type;
                if (type.isDynamicType()) {
                    dynamicCnt++;
                }
                staticSize += type.getFixedSize();
            }

            byte[][] bb = new byte[args.length + dynamicCnt][];
            for (int curDynamicPtr = staticSize, curDynamicCnt = 0, i = 0; i < args.length; i++) {
                SolidityType type = inputs.get(i).type;
                if (type.isDynamicType()) {
                    byte[] dynBB = type.encode(args[i]);
                    bb[i] = SolidityType.IntType.encodeInt(curDynamicPtr);
                    bb[args.length + curDynamicCnt] = dynBB;
                    curDynamicCnt++;
                    curDynamicPtr += dynBB.length;
                } else {
                    bb[i] = type.encode(args[i]);
                }
            }

            return BytesUtils.merge(bb);
        }
    }

    public static class Constructor extends Entry {

        public Constructor(List<Param> inputs) {
            super(null, "", inputs, null, Type.constructor, false);
        }

        public byte[] encode(Object... args) {
            return encodeArguments(args);
        }

        public List<?> decode(byte[] encoded) {
            return Param.decodeList(inputs, encoded);
        }

        public String formatSignature(String contractName) {
            return format("function %s(%s)", contractName, join(inputs, ", "));
        }

        @Override
        public String toString() {
            return format("constructor (%s)", join(inputs, ", "));
        }

    }

    public static class Offchain extends Entry {
        private static final int ENCODED_SIGN_LENGTH = 4;

        public Offchain(String name, List<Param> inputs, List<Param> outputs, Boolean payable) {
            super(null, name, inputs, outputs, Type.offchain, payable);
        }

        public List<?> decodeOutput(byte[] encoded) {
            return Param.decodeList(outputs, encoded);
        }

        public List<?> decode(byte[] encoded) {
            return Param.decodeList(inputs, subarray(encoded, ENCODED_SIGN_LENGTH, encoded.length));
        }

        public byte[] encode(Object... args) {
            return BytesUtils.merge(encodeSignature(), encodeArguments(args));
        }

        @Override
        public byte[] encodeSignature() {
            return extractSignature(super.encodeSignature());
        }

        public static byte[] extractSignature(byte[] data) {
            return subarray(data, 0, ENCODED_SIGN_LENGTH);
        }

        @Override
        public String toString() {
            return format("getter %s(%s)", name, join(inputs, ", "));
        }
    }

    public static class Function extends Entry {

        private static final int ENCODED_SIGN_LENGTH = 4;

        public Function(String name, List<Param> inputs, List<Param> outputs, Boolean payable, Type type) {
            super(null, name, inputs, outputs, type, payable);
        }

        public List<?> decode(byte[] encoded) {
            return Param.decodeList(inputs, subarray(encoded, ENCODED_SIGN_LENGTH, encoded.length));
        }

        public List<?> decodeOutput(byte[] encoded) {
            return Param.decodeList(outputs, encoded);
        }

        public byte[] encode(Object... args) {
            return BytesUtils.merge(encodeSignature(), encodeArguments(args));
        }

        @Override
        public byte[] encodeSignature() {
            return extractSignature(super.encodeSignature());
        }

        public static byte[] extractSignature(byte[] data) {
            return subarray(data, 0, ENCODED_SIGN_LENGTH);
        }

        @Override
        public String toString() {
            return format("function %s(%s)", name, join(inputs, ", "));
        }
    }

    public static class Event extends Entry {

        public Event(boolean anonymous, String name, List<Param> inputs) {
            super(anonymous, name, inputs, null, Type.event, false);
        }

        public List<?> decode(byte[] data, byte[][] topics) {
            List<Object> result = new ArrayList<>(inputs.size());

            byte[][] argTopics = anonymous ? topics : subarray(topics, 1, topics.length);
            List<?> indexed = Param.decodeList(filteredInputs(true), BytesUtils.merge(argTopics));
            List<?> notIndexed = Param.decodeList(filteredInputs(false), data);

            for (Param input : inputs) {
                result.add(input.indexed ? indexed.remove(0) : notIndexed.remove(0));
            }

            return result;
        }

        public List<?> decode(byte[] data, List<Hash> topics) {
            if (CollectionUtils.isEmpty(topics)) {
                return null;
            }
            return decode(data, convertTopics(topics));
        }

        private List<Param> filteredInputs(final boolean indexed) {
            return ListUtils.select(inputs, param -> param.indexed == indexed);
        }

        @Override
        public String toString() {
            return format("event %s(%s)", name, join(inputs, ", "));
        }
    }
}
