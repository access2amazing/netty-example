package io.netty.example.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xueli.wang
 * @since 2020/06/14 20:39
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当一个连接被建立并准备好产生traffic时，channelActive()方法被调用
     * 本方法中将写表示时间的32位整数
     * @param ctx ChannelHandlerContext
     * @throws Exception Exception
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        // 32位整数需要分配至少4个字节
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));


        final ChannelFuture channelFuture = ctx.writeAndFlush(time);
        // 可以简化为 channelFuture.addListener(ChannelFutureListener.CLOSE)
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                assert channelFuture == future;
                ctx.close();
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
