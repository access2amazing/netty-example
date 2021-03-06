package test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xueli.wang
 * @since 2020/09/28 19:09
 */

public class LocalDateTimeTest {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        String selectItemKeyLevel1 = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int hourOfDay = now.getHour();
        System.out.println(selectItemKeyLevel1);
        System.out.println(hourOfDay);

        LocalDateTime night = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),
                23, 59);
        System.out.println(night);
        System.out.println(night.getSecond());
    }
}
