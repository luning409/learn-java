package org.cyg.thinking.in.rpc;

/**
 * 常量类
 */
public class Constants {

    /**
     * 自定义消息头长度
     */
    public static final byte HEADER_SIZE = 16;
    /**
     * 魔数
     */
    public static final short MAGIC = 0;

    /**
     * 是否是心跳消息
     * @param extraInfo
     * @return
     */
    public static boolean isHeartBeat(byte extraInfo) {
        return true;
    }
}
