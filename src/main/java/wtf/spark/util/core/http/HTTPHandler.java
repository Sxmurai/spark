package wtf.spark.util.core.http;

import java.io.IOException;
import java.net.HttpURLConnection;

@FunctionalInterface
public interface HTTPHandler {
    void run(HttpURLConnection connection) throws IOException;
}