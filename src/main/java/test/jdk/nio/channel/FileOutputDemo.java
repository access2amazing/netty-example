package test.jdk.nio.channel;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xueli.wang
 * @since 2020/11/15 11:39
 */

public class FileOutputDemo {
    private static final byte[] MESSAGE = {83, 111, 109, 101, 32, 98, 121, 116, 101, 115, 46};

    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("D://temp/test.txt");

        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        for (byte b : MESSAGE) {
            byteBuffer.put(b);
        }

        byteBuffer.flip();

        fileChannel.write(byteBuffer);

        fileOutputStream.close();
    }
}
