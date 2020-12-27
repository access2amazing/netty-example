package io.netty.tomcat.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author xueli.wang
 * @since 2020/11/15 12:33
 */

public class Response {
    private OutputStream out;

    private ChannelHandlerContext channelHandlerContext;

    public Response(OutputStream out) {
        this.out = out;
    }

    public Response(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public void write(String s) throws Exception {
        String response = "HTTP/1.1 200 OK\n"
                + "Content-Type: text/html;\n"
                + "\r\n"
                + s;
        out.write(response.getBytes());
    }

    public void nettyWrite(String out) throws Exception {
        try {
            if (out == null || out.length() == 0) {
                return;
            }

            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(out.getBytes(StandardCharsets.UTF_8)));

            response.headers().set("Content-Type", "text/html");

            channelHandlerContext.write(response);
        } finally {
            channelHandlerContext.flush();
            channelHandlerContext.close();
        }
    }
}
