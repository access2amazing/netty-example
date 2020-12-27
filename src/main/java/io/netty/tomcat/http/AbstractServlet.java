package io.netty.tomcat.http;

/**
 * @author xueli.wang
 * @since 2020/11/15 12:24
 */

public abstract class AbstractServlet {
    private static final String GET = "GET";

    public void service(Request request, Response response) throws Exception {
        if (GET.equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    /**
     * get
     * @param request 请求体
     * @param response 响应体
     * @throws Exception 异常
     */
    public abstract void doGet(Request request, Response response) throws Exception;

    /**
     * post
     * @param request 请求体
     * @param response 响应体
     * @throws Exception 异常
     */
    public abstract void doPost(Request request, Response response) throws Exception;
}
