package io.netty.example.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.text.DecimalFormat;

/**
 * @author xueli.wang
 * @since 2020/06/14 18:04
 */
public class DiscardServer {
    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // NioEventLoopGroup是处理I/O操作的多线程event loop
        // netty为不同的传输提供了EventLoopGroup
        // 在服务侧应用中，使用了两个NioEventLoopGroup
        // boss接收来自客户端的连接，worker处理已接收连接的traffic
        // boss接收连接后会将连接注册到worker上
        // 线程的数量以及到channel的映射取决于EventLoopGroup的实现，以及通过构造器进行配置
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            // ServerBootstrap是用于启动服务器的helper类
            // 也可以直接使用Channel启动服务器，但过程比较繁琐
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    // 指定实例化Channel的类为NioServerSocket，用来接收连接
                    .channel(NioServerSocketChannel.class)
                    // 此处指定的handler将始终由新接收的Channel evaluate。
                    // ChannelInitializer是一个特殊的handler，用于帮助用户配置一个新的Channel
                    // 可以通过添加一些像DiscardServerHandler这样的handler来配置新Channel的ChannelPipeline
                    // 随着应用变得复杂，需要向pipeline添加更多的handler，最后该匿名类将会被提取出来
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    // 向Channel实现设置参数
                    // 在TCP/IP服务器中，设置比如tcpNoDelay和keepAlive这样的socket参数
                    // 可以参照ChannelOption、ChannelConfig的实现类的api文档
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // option()用于接收连接的NioServerSocketChannel
                    // childOption()用于被父级ServerChannel接收的Channel
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // bind and start to accept incoming connections
            // 绑定到网卡（NICs）的8080端口
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

            // wait util the server socket is closed
            // in this example, this does not happen, but you can do that to gracefully
            // shut down your server
            channelFuture.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        double d = 13.10001d;
        double precision = 0.09d;
        System.out.println(
                Math.abs(Math.round(d) - d) <= precision
                ? String.format("%.0f", d)
                : String.format("%.1f", d)
        );
    }
}
