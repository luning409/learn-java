package org.cyg.thinking.in.rpc.factory;

import org.cyg.thinking.in.rpc.serialization.HessianSerialization;
import org.cyg.thinking.in.rpc.serialization.Serialization;

import java.util.ArrayList;
import java.util.List;

public class SerializationFactory {

    private final static List<Serialization> SERIALIZATIONS;

    static {
        List<Serialization> list = new ArrayList<>();
        list.add(new HessianSerialization());

        SERIALIZATIONS = list.subList(0, list.size());
    }

    public static Serialization get(byte extraInfo) {
        return SERIALIZATIONS.get(extraInfo);
    }
}
