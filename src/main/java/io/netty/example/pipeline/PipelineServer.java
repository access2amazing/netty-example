package io.netty.example.pipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.example.pipeline.inbound.InboundHandlerA;
import io.netty.example.pipeline.inbound.InboundHandlerB;
import io.netty.example.pipeline.inbound.InboundHandlerC;
import io.netty.example.pipeline.outbound.OutboundHandlerA;
import io.netty.example.pipeline.outbound.OutboundHandlerB;
import io.netty.example.pipeline.outbound.OutboundHandlerC;

/**
 * @author xueli.wang
 * @since 2020/12/27 19:49
 */

public class PipelineServer {
    public static void main(String[] args) throws Exception {
        PipelineServer pipelineServer = new PipelineServer();
        pipelineServer.start(9090);
    }

    private void start(int port) throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new InboundHandlerA());
                            socketChannel.pipeline().addLast(new InboundHandlerB());
                            socketChannel.pipeline().addLast(new InboundHandlerC());

                            socketChannel.pipeline().addLast(new OutboundHandlerA());
                            socketChannel.pipeline().addLast(new OutboundHandlerB());
                            socketChannel.pipeline().addLast(new OutboundHandlerC());
                        }
                    }).childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

            channelFuture.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
