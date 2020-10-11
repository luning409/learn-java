package org.cyg.thinking.in.rpc.compress;

import org.xerial.snappy.Snappy;

import java.io.IOException;

public class SnappyCompress implements Compress {

    @Override
    public byte[] compress(byte[] array) throws IOException {
        if (array == null) { return null; }
        return Snappy.compress(array);
    }

    @Override
    public byte[] uncompress(byte[] array) throws IOException {
        if (array == null) { return null; }
        return Snappy.uncompress(array);
    }
}
