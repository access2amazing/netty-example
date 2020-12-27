package test.jdk.nio.buffer;

import java.io.FileInputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xueli.wang
 * @since 2020/11/14 21:10
 */

public class BufferDemo {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("D://temp/test.txt");
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(10);
        output("buffer init", buffer);

        fileChannel.read(buffer);
        output("read invoke", buffer);

        buffer.flip();
        output("flip invoke", buffer);

        while (buffer.remaining() > 0) {
            buffer.get();
        }
        output("get invoke", buffer);

        buffer.clear();
        output("clear invoke", buffer);

        fileInputStream.close();
    }

    private static void output(String step, Buffer buffer) {
        System.out.println(step + " : ");
        System.out.print("capacity: " + buffer.capacity() + ", ");
        System.out.print("position: " + buffer.position() + ", ");
        System.out.println("limit: " + buffer.limit());
    }
}
