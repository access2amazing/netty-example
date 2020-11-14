package test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xueli.wang
 * @since 2020/10/16 10:24
 */

public class DateCompareTest {
    public static void main(String[] args) throws Exception {
        Date date = new Date(System.currentTimeMillis());

        Thread.sleep(1000 * 5);

        Date date1 = new Date(System.currentTimeMillis());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(format.format(date1) + " after " + format.format(date) + ": " + (date1.compareTo(date) >= 0));
    }
}
