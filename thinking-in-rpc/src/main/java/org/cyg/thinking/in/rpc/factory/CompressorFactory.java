package org.cyg.thinking.in.rpc.factory;

import org.cyg.thinking.in.rpc.compress.Compress;
import org.cyg.thinking.in.rpc.compress.SnappyCompress;
import org.cyg.thinking.in.rpc.serialization.HessianSerialization;
import org.cyg.thinking.in.rpc.serialization.Serialization;

import java.util.ArrayList;
import java.util.List;

public class CompressorFactory {

    private final static List<Compress> COMPRESSES;

    static {
        List<Compress> list = new ArrayList<>();
        list.add(new SnappyCompress());

        COMPRESSES = list.subList(0, list.size());
    }

    public static Compress get(byte extraInfo) {
        return COMPRESSES.get(extraInfo);
    }
}
