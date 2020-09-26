package io.netty.example.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * ByteToMessageDecoder继承了ChannelInboundHandlerAdapter，用于解决分片问题
 * @author xueli.wang
 * @since 2020/06/17 20:25
 */
public class TimeDecoder extends ByteToMessageDecoder {
    /**
     * 当新的数据到达后，ByteToMessageDecoder将调用decode()方法，将数据添加到buf中
     * @param channelHandlerContext channelHandlerContext
     * @param in in
     * @param out out
     * @throws Exception ex
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            // 当in中没有足够长度的数据时，decode可以决定不往out中添加数据
            return;
        }

        // 往out中添加对象时表示解码出一条消息，ChannelHandlerContext将丢弃已累积的buffer
        out.add(in.readBytes(4));
    }
}
