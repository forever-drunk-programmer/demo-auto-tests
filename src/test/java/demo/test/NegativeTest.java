package demo.test;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

public class NegativeTest extends AbstractCleanTest {
    private static final String CREDENTIALS = Credentials.basic("admin", "admin");
    private static final String BASE_URL = "http://localhost:8080";
    private final OkHttpClient client = new OkHttpClient.Builder().build();

    @Test
    public void authRequestRandomUrl() throws IOException {
        Request request = new Request.Builder()
                .addHeader("Authorization", CREDENTIALS)
                .get()
                .url(BASE_URL + "/" + UUID.randomUUID())
                .build();

        Response response = client.newCall(request).execute();
        assertNotFound(response);
    }

    @Test
    public void requestRandomUrl() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(BASE_URL + "/" + UUID.randomUUID())
                .build();

        Response response = client.newCall(request).execute();
        assertNotFound(response);
    }
}
