package test;

/**
 * @author xueli.wang
 * @since 2020/11/13 09:15
 */

public class TryCatchException {
    public static void main(String[] args) {
        String i = fo();
        System.out.println(i);
    }

    private static String fo() {
        String i = new String();
        System.out.println(i);

        try {
            i = "ymm";
            int j = 10 / 0;
            return i;
        } catch (Exception e) {
            i = "hcb";
            return i;
        } finally {
            i = "mb";
            System.out.println(i);
        }
    }

    static class Test {
        String test;
    }
}
