package io.netty.tomcat.servlet;

import io.netty.tomcat.http.AbstractServlet;
import io.netty.tomcat.http.Request;
import io.netty.tomcat.http.Response;

/**
 * @author xueli.wang
 * @since 2020/11/15 12:43
 */

public class FirstServlet extends AbstractServlet {
    @Override
    public void doGet(Request request, Response response) throws Exception {
        doPost(request, response);
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
        if (request.getHttpRequest() != null) {
            response.nettyWrite("This is FirstServlet");
            return;
        }
        response.write("This is FirstServlet");
    }
}
