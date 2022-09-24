package wtf.spark.util.core.fs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileSystemUtil {

    public static void write(File file, String content) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            write(Files.newOutputStream(file.toPath()), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(OutputStream stream, String content) {
        if (stream == null) {
            return;
        }

        try {
            stream.write(content.getBytes(StandardCharsets.UTF_8), 0, content.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String read(File file) {
        try {
            return read(Files.newInputStream(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String read(InputStream stream) {
        if (stream == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        try {
            int i;
            while ((i = stream.read()) != -1) {
                builder.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
