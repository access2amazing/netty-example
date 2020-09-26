package io.netty.example.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xueli.wang
 * @since 2020/06/14 16:25
 *
 * ChannelInboundHandlerAdapter实现了ChannelInboundHandler
 * ChannelInboundHandler提供了多种可以覆写的event handler方法
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 覆写channelRead() event handler
     * 该方法在收到来自客户端的message时调用
     * 常规的覆写如下：
     *     try {
     *         // Do something with msg
     *     } finally {
     *         ReferenceCountUtil.release(msg);
     *     }
     * @param ctx ChannelHandlerContext
     * @param msg ByteBuf
     * @throws Exception IO
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // discard the received data silently
        // 实现DISCARD协议需要丢弃收到的message
        // 由于ByteBuf是一个引用计数(reference-count)对象，需要通过调用release()方法显式地释放
        ((ByteBuf) msg).release();
    }

    /**
     * 当发生IO error或者event handler的异常时调用
     * 常规的处理记录异常以及关闭相关的channel
     * 或者在关闭连接之前向客户端发送包含error code的响应
     * @param ctx ChannelHandlerContext
     * @param cause Throwable
     * @throws Exception Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // close the connection when an exception is raised
        cause.printStackTrace();
        ctx.close();
    }
}
