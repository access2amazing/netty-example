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

        // ByteBuf有两个指针：一个用于读，另一个用于写
        // 当写数据到ByteBuf时，写index增加，而读index不变
        // 读index和写index分别代表消息开始和结束的位置
        // ChannelHandlerContext#write以及ChannelHandlerContext#writeAndFlush方法
        // 会返回一个ChannelFuture对象，表示可能没有完成的I/O操作（Netty是异步的）
        // 因此，在调用close方法前需要确保ChannelFuture已经完成（可以通过添加listener实现）
        // 注意：close方法会返回一个ChannelFuture，表示连接不会立即被关闭
        final ChannelFuture channelFuture = ctx.writeAndFlush(time);

        // 添加ChannelFutureListener
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
