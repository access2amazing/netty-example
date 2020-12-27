package io.netty.tomcat.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author xueli.wang
 * @since 2020/11/15 12:26
 */

public class Request {
    private String method;

    private String url;

    private ChannelHandlerContext context;

    private HttpRequest httpRequest;

    public Request(InputStream in) {
        try {
            String content = "";
            byte[] buff = new byte[1024];
            int len = 0;
            if ((len = in.read(buff)) > 0) {
                content = new String(buff, 0, len);
            }

            String line = content.split("\\n")[0];
            String[] arr = line.split("\\s");

            this.method = arr[0];
            this.url = arr[1].split("\\?")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Request(ChannelHandlerContext context, HttpRequest httpRequest) {
        this.context = context;
        this.httpRequest = httpRequest;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public ChannelHandlerContext getContext() {
        return context;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public Map<String, List<String >> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(httpRequest.uri());
        return decoder.parameters();
    }

    public String getParameter(String name) {
        Map<String, List<String>> params = getParameters();
        List<String> param = params.get(name);
        return param == null ? null : param.get(0);
    }
}
