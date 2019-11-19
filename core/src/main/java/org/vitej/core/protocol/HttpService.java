package org.vitej.core.protocol;

import okhttp3.*;
import org.vitej.core.exception.ConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpService extends Service {
    public static final String DEFAULT_URL = "http://127.0.0.1:48132";

    private final String url;
    private OkHttpClient httpClient;
    private HashMap<String, String> headers = new HashMap<>();


    public HttpService() {
        this(DEFAULT_URL);
    }

    public HttpService(String url) {
        this(url, createOkHttpClient());
    }

    public HttpService(String url, OkHttpClient httpClient) {
        super();
        this.url = url;
        this.httpClient = httpClient;
    }

    @Override
    public void close() throws IOException {
    }

    private static OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    public static final MediaType JSON_MEDIA_TYPE =
            MediaType.parse("application/json; charset=utf-8");

    @Override
    protected InputStream performIO(String request) throws IOException {

        RequestBody requestBody = RequestBody.create(JSON_MEDIA_TYPE, request);
        Headers headers = buildHeaders();

        okhttp3.Request httpRequest =
                new okhttp3.Request.Builder().url(url).headers(headers).post(requestBody).build();

        okhttp3.Response response = httpClient.newCall(httpRequest).execute();
        ResponseBody responseBody = response.body();
        if (response.isSuccessful()) {
            if (responseBody != null) {
                return responseBody.byteStream();
            } else {
                return null;
            }
        } else {
            int code = response.code();
            String text = responseBody == null ? "N/A" : responseBody.string();

            throw new ConnectionException("connection failed: " + code + " " + text);
        }
    }

    private Headers buildHeaders() {
        return Headers.of(headers);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addHeaders(Map<String, String> headersToAdd) {
        headers.putAll(headersToAdd);
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }
}
