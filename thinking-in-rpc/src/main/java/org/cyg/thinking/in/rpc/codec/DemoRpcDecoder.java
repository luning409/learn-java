package org.cyg.thinking.in.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.cyg.thinking.in.rpc.Constants;
import org.cyg.thinking.in.rpc.compress.Compress;
import org.cyg.thinking.in.rpc.factory.CompressorFactory;
import org.cyg.thinking.in.rpc.factory.SerializationFactory;
import org.cyg.thinking.in.rpc.protocol.Header;
import org.cyg.thinking.in.rpc.protocol.Message;
import org.cyg.thinking.in.rpc.protocol.Request;
import org.cyg.thinking.in.rpc.serialization.Serialization;

import java.util.List;

public class DemoRpcDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        // 不到16字节的话无法解析消息头，暂不读取
        if (byteBuf.readableBytes() < Constants.HEADER_SIZE) {
            return ;
        }
        // 记录当前 readIndex 指针的位置, 方便重置
        byteBuf.markReaderIndex();
        // 尝试读取消息头的魔数部分
        short magic = byteBuf.readShort();
        // 魔数不匹配会抛出异常
        if (magic != Constants.MAGIC) {
            byteBuf.resetReaderIndex();
            throw new RuntimeException("magic number error:" + magic);
        }

        // 依次读取消息版本、附加信息、消息ID以及消息体长度四部分
        byte version = byteBuf.readByte();
        byte extraInfo = byteBuf.readByte();
        long messageId = byteBuf.readLong();
        int size = byteBuf.readInt();
        Object request = null;
        // 心跳消息是没有消息体的, 无须读取
        if (!Constants.isHeartBeat(extraInfo)) {
            // 对于非心跳消息, 没有积累到足够的消息是无法进行反序列化的
            if (byteBuf.readableBytes() < size) {
                byteBuf.resetReaderIndex();
                return ;
            }
            // 读取消息体并进行反序列化
            byte[] payload = new byte[size];
            byteBuf.readBytes(payload);
            // 这里根据消息头中的extraInfo部分选择相应的序列化和压缩方式
            Serialization serialization = SerializationFactory.get(extraInfo);
            Compress compress = CompressorFactory.get(extraInfo);
            // 经过解压缩和反序列化得到消息体
            request = serialization.deSerialize(compress.uncompress(payload), Request.class);
        }
        // 将上面读取到的消息头和消息体拼装成完整的Message并向后传递
        Header header = new Header(magic, version, extraInfo, messageId, size);
        Message message = new Message(header, request);
        out.add(message);

    }
}
