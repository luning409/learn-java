package org.cyg.thinking.in.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.cyg.thinking.in.rpc.Constants;
import org.cyg.thinking.in.rpc.compress.Compress;
import org.cyg.thinking.in.rpc.factory.CompressorFactory;
import org.cyg.thinking.in.rpc.factory.SerializationFactory;
import org.cyg.thinking.in.rpc.protocol.Header;
import org.cyg.thinking.in.rpc.protocol.Message;
import org.cyg.thinking.in.rpc.serialization.Serialization;

public class DemoRpcEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        Header header = msg.getHeader();
        // 依次序列化消息头中的魔数、版本、附加信息以及消息ID
        out.writeShort(header.getMagic());
        out.writeByte(header.getVersion());
        out.writeByte(header.getExtraInfo());
        out.writeLong(header.getMessageId());

        if (Constants.isHeartBeat(header.getExtraInfo())) {
            // 心跳消息，没有消息体，这里写入0
            out.writeInt(0);
            return ;
        }
        Object content = msg.getRequest();
        // 按照extraInfo部分指定的序列化方式和压缩方式进行处理
        Serialization serialization = SerializationFactory.get(header.getExtraInfo());
        Compress compress = CompressorFactory.get(header.getExtraInfo());
        // 按照extraInfo部分指定的序列化方式和压缩方式进行处理
        byte[] payload = compress.compress(serialization.serialize(content));
        // 写入消息体长度
        out.writeInt(payload.length);
        // 写入消息体
        out.writeBytes(payload);
    }
}
