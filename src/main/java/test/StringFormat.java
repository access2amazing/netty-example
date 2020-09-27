package test;

/**
 * @author xueli.wang
 * @since 2020/09/26 16:44
 */

public class StringFormat {
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
