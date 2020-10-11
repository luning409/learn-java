package org.cyg.thinking.in.rpc.compress;

import java.io.IOException;

/**
 * 压缩/解压 的方式
 */
public interface Compress {

    byte[] compress(byte[] array) throws IOException;

    byte[] uncompress(byte[] array) throws IOException;

}
