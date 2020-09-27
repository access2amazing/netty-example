package io.netty.example.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author xueli.wang
 * @since 2020/06/14 20:59
 */
public class TimeClient {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 9090;
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            // Bootstrap与ServerBootstrap区别在于：
            // 它用于非服务器channel，例如客户端或无连接channel
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker)
                    // 客户端使用NioSocketChannel
                    .channel(NioSocketChannel.class)
                    // 因为客户端SocketChannel没有父级channel，因此不会使用childOption()
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeDecoder(), new TimeClientHandler());
                        }
                    });

            // 连接到服务器，而不是绑定端口
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            channelFuture.channel().close().sync();
        } finally {
            worker.shutdownGracefully();
        }
    }
}
