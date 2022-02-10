package demo.utils;

import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public abstract class AbstractApplicationClient {

    private final String BASE_URL;
    private final OkHttpClient client;
    private final String CREDENTIALS = Credentials.basic("admin", "admin");
    private final MediaType APPLICATION_JSON = MediaType.parse("application/json");

    public AbstractApplicationClient(String baseUrl) {
        BASE_URL = baseUrl;
        client = new OkHttpClient.Builder().build();
    }

    public Response createNewRaw(String json) throws IOException {
        RequestBody body = RequestBody.create(json, APPLICATION_JSON);

        Request request = new Request.Builder()
                .addHeader("Authorization", CREDENTIALS)
                .post(body)
                .url(BASE_URL)
                .build();

        return client.newCall(request).execute();
    }

    public Response getTodosRaw(Integer offset, Integer limit) throws IOException {
        String getUrl = BASE_URL;

        if (offset != null || limit != null) {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
            if (offset != null) {
                urlBuilder.addQueryParameter("offset", offset.toString());
            }
            if (limit != null) {
                urlBuilder.addQueryParameter("limit", limit.toString());
            }
            getUrl = urlBuilder.build().toString();
        }

        Request.Builder requestBuilder = new Request.Builder()
                .addHeader("Authorization", CREDENTIALS)
                .addHeader("Content-Type", "application/json")
                .get()
                .url(getUrl);

        Request request = requestBuilder.build();

        return client.newCall(request).execute();
    }

    public Response updateTodos(Long id, String json) throws IOException {
        RequestBody body = RequestBody.create(json, APPLICATION_JSON);

        Request request = new Request.Builder()
                .addHeader("Authorization", CREDENTIALS)
                .put(body)
                .url(BASE_URL + "/" + id)
                .build();

        return client.newCall(request).execute();

    }

    public Response deleteTodosRaw(Long id) throws IOException {
        Request request = new Request.Builder()
                .addHeader("Authorization", CREDENTIALS)
                .delete()
                .url(BASE_URL + "/" + id)
                .build();

        return client.newCall(request).execute();
    }

    public Response deleteTodosRaw(String id) throws IOException {
        Request request = new Request.Builder()
                .addHeader("Authorization", CREDENTIALS)
                .delete()
                .url(BASE_URL + "/" + id)
                .build();

        return client.newCall(request).execute();
    }
}
