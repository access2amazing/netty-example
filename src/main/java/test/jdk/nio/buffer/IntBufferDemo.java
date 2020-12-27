package test.jdk.nio.buffer;

import java.nio.IntBuffer;

/**
 * @author xueli.wang
 * @since 2020/11/14 20:54
 */

public class IntBufferDemo {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(8);

        for (int i = 0; i < buffer.capacity(); i++) {
            int j = 2 * (i + 1);
            buffer.put(j);
        }

        buffer.flip();
        while (buffer.hasRemaining()) {
            int j = buffer.get();
            System.out.print(j + "  ");
        }
    }
}
