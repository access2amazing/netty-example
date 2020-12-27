package io.netty.tomcat.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author xueli.wang
 * @since 2020/11/15 12:51
 */

public class Tomcat {
    private Map<String, AbstractServlet> servletMap = new HashMap<>();

    private Properties web = new Properties();

    public static void main(String[] args) {
        new Tomcat().start();
    }

    private void init() {
        try {
            String webInf = this.getClass().getResource("/").getPath();
            FileInputStream fileInputStream = new FileInputStream(webInf + "web.properties");

            web.load(fileInputStream);

            for (Object k : web.keySet()) {
                String key = k.toString();
                if (key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = web.getProperty(key);
                    String className = web.getProperty(servletName + ".className");

                    AbstractServlet servlet = (AbstractServlet) Class.forName(className).newInstance();
                    servletMap.put(url, servlet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        init();

        try {
            int port = 8080;
            ServerSocket server = new ServerSocket(port);

            System.out.println("Tomcat started, listening port: " + port);

            while (true) {
                Socket client = server.accept();
                process(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nettyStart() {
        init();

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();

            server.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel client) throws Exception {
                            client.pipeline().addLast(new HttpResponseEncoder());
                            client.pipeline().addLast(new HttpRequestDecoder());
                            client.pipeline().addLast(new NettyBasedTomcatHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            int port = 9090;
            ChannelFuture channelFuture = server.bind(port).sync();
            System.out.println("nettyBasedTomcat started, listening port: " + port);

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    private void process(Socket client) throws Exception {
        InputStream inputStream = client.getInputStream();
        OutputStream outputStream = client.getOutputStream();

        Request request = new Request(inputStream);
        Response response = new Response(outputStream);

        String url = request.getUrl();

        if (servletMap.containsKey(url)) {
            servletMap.get(url).service(request, response);
        } else {
            response.write("404 - Not Found");
        }

        outputStream.flush();
        outputStream.close();

        inputStream.close();
        client.close();
    }

    class NettyBasedTomcatHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof HttpRequest) {
                System.out.println("hello");
                HttpRequest httpRequest = (HttpRequest) msg;

                Request request = new Request(ctx, httpRequest);
                Response response = new Response(ctx);

                String url = httpRequest.uri();
                if (servletMap.containsKey(url)) {
                    servletMap.get(url).service(request, response);
                } else {
                    response.nettyWrite("404 - Not Found");
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
        }
    }
}
