package ecse321.mcgill.ca.academicmanager_android;
import android.content.Context;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

public class HttpUtils {

    public static final String DEFAULT_BASE_URL = "https://cooperatorapp-backend-18.herokuapp.com/";

    private static String baseUrl;
    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        baseUrl = DEFAULT_BASE_URL;
    }

    public static void get(String url, RequestParams params, String token, AsyncHttpResponseHandler responseHandler) {
        if (token != null) {
            String authHeader = "Bearer " + token;
            client.addHeader("Authorization", authHeader);
        }
        client.get(baseUrl + url, params, responseHandler);
    }

    public static void post(Context context, String url, HttpEntity entity, String contentType, String token, AsyncHttpResponseHandler responseHandler) {
        if (token != null) {
            String authHeader = "Bearer " + token;
            client.addHeader("Authorization", authHeader);
        }
        client.post(context, baseUrl + url, entity, contentType, responseHandler);
    }

    public static void put(Context context, String url, HttpEntity entity, String contentType, String token, AsyncHttpResponseHandler responseHandler) {
        if (token != null) {
            String authHeader = "Bearer " + token;
            client.addHeader("Authorization", authHeader);
        }
        client.put(context, baseUrl + url, entity, contentType, responseHandler);
    }
}