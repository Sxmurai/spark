package wtf.spark.util.core.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static void get(String u, HTTPHandler runnable) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(u).openConnection();
            connection.setReadTimeout(5000);
            connection.setUseCaches(false);

            connection.connect();
            runnable.run(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface HTTPHandler {
        void run(HttpURLConnection connection) throws IOException;
    }
}
