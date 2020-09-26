package io.netty.example.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xueli.wang
 * @since 2020/06/14 20:17
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Netty将其写出到网络中后会释放msg
        ctx.write(msg);
        // ctx.write(Object)不会将msg写出到网络中，msg被放到了缓冲区
        // 然后通过ctx.flush（）flush到网络中
        // 可以用 ctx.writeAndFlush() 替代
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
