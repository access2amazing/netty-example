package io.netty.example.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author xueli.wang
 * @since 2020/06/17 19:30
 */
public class FirstTimeClientHandler extends ChannelInboundHandlerAdapter {
    private ByteBuf buf;

    /**
     * 生命周期监听方法
     * 执行不会长时间阻塞的初始化任务
     * @param ctx ChannelHandlerContext
     * @throws Exception ex
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        buf = ctx.alloc().buffer(4);
    }

    /**
     * 生命周期监听方法
     * 执行不会长时间阻塞的去初始化任务
     * @param ctx ChannelHandlerContext
     * @throws Exception ex
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        buf.release();
        buf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf m = (ByteBuf) msg;
        // 所有接收到的数据被放到buf中
        buf.writeBytes(m);
        m.release();

        // 当buf中有4个字节可读时，读取buf中的数据，执行后续逻辑
        if (buf.readableBytes() >= 4) {
            long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
