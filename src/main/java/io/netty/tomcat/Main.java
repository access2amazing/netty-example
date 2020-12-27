package io.netty.tomcat;

import io.netty.tomcat.http.Tomcat;

/**
 * @author xueli.wang
 * @since 2020/11/15 13:07
 */

public class Main {
    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        /// tomcat.start();
        tomcat.nettyStart();
    }
}
