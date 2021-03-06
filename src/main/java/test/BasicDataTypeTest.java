package test;

import java.util.Date;

/**
 * @author xueli.wang
 * @since 2021/02/23 19:30
 */

public class BasicDataTypeTest {
    public static void main(String[] args) {
        // -0.0F
        float negativeZero = Float.intBitsToFloat(0x80000000);
        System.out.println(negativeZero);
        System.out.println(negativeZero == 0F);

        // 正无穷
        float positiveInfinite = 1 / 0F;
        System.out.println(positiveInfinite);
        System.out.println(Float.isInfinite(positiveInfinite));
        System.out.println(Float.floatToRawIntBits(positiveInfinite));

        // 负无穷
        float negativeInfinite = 1 / negativeZero;
        System.out.println(negativeInfinite);
        System.out.println(Float.isInfinite(negativeInfinite));

        System.out.println(Integer.toHexString(Float.floatToRawIntBits(positiveInfinite)));
        System.out.println(Integer.toHexString(Float.floatToRawIntBits(negativeInfinite)));

        int temp = Float.floatToRawIntBits(negativeInfinite) | 1;
        System.out.println(Integer.toHexString(temp));
        float nan = Float.intBitsToFloat(temp);
        System.out.println(nan);

        String s = "今天下午18:00";
        System.out.println(s.indexOf("午"));

        System.out.println(new Date(Long.MIN_VALUE));
    }
}
