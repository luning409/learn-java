package org.cyg.thinking.in.rpc.serialization;

import java.io.IOException;

/**
 * 序列化和反序列化操作
 */
public interface Serialization {

    <T> byte[] serialize(T obj) throws IOException;

    <T> T deSerialize(byte[] data, Class<T> clz) throws IOException;

}
