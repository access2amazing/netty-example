package test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xueli.wang
 * @since 2020/12/18 14:28
 */

public class GenericsTest {
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();

        Class stringListClass = stringList.getClass();
        Class integerListClass = integerList.getClass();

        // true 编译后类型擦除
        System.out.println(stringListClass.equals(integerListClass));
    }
}
