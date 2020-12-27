package test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author xueli.wang
 * @since 2020/12/01 19:10
 */

public class StreamCompareMax {
    public static void main(String[] args) {
        List<ExtensionPriority> a = new ArrayList<>();
        a.add(new ExtensionPriority(0, 100));
        a.add(new ExtensionPriority(0, 2));
        a.add(new ExtensionPriority(0, 3));
        a.add(new ExtensionPriority(0, 4));

        ExtensionPriority b = a.stream().max(Comparator.comparing((c) -> {
            return c.priority;
        })).get();
        System.out.println(b.index);
    }

    static class ExtensionPriority {
        int priority = 0;
        int index;

        ExtensionPriority(int priority, int index) {
            this.priority = priority;
            this.index = index;
        }
    }
}
