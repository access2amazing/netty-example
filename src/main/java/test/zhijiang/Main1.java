package test.zhijiang;

import java.util.Scanner;

/**
 * @author xueli.wang
 * @since 2020/12/20 23:27
 */

public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        if (m > n) {
            System.out.println(-1);
            return;
        }

        if (m == 1 && n == 1) {
            System.out.println(1);
            return;
        }

        System.out.println((m + n - 2) * 2 + 1);
    }
}
