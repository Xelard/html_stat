package services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;


public class HttpClient {
    private static final Logger log = Logger.getLogger(HttpClient.class.getName());

    private final URL url;

    public HttpClient(String url) throws Exception {
        this.url = new URL(url);
        checkURL(this.url);
    }

    private static void checkContentType(URLConnection connection) {
        String contentType = connection.getContentType();
        if (contentType == null || !contentType.startsWith("text/")) {
            throw new IllegalStateException("Unsupported content type. Expected: text. Got: " + contentType);
        }
    }

    private static void checkURL(URL url) {
            String protocol = url.getProtocol();
            if (!protocol.startsWith("http"))
                throw new IllegalArgumentException("Supported protocols: http, https. Got: " + protocol);
    }

    public HttpResponse connect() throws IOException {
        log.info("Connecting to " + url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        checkContentType(connection);

        return new HttpResponse(connection);
    }


}

