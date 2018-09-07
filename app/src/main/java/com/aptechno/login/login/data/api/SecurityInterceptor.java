package com.aptechno.login.login.data.api;

import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;


import com.aptechno.login.login.data.local.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static android.text.TextUtils.isEmpty;



/**
 * Created by ankit on 25/10/16.
 * An okHttp interceptor that adds security headers to the api call
 * as well as provided the time
 */
public class SecurityInterceptor implements Interceptor {
    private static long elapsedTime;
    private final PrefManager prefManager;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    SecurityInterceptor(PrefManager prefManager) {

        this.prefManager = prefManager;
    }

    public static long getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final long time = SystemClock.elapsedRealtime();

        final Request originalRequest = chain.request();

        final String accessToken = "Bearer " + PrefManager.getInstance().getAccessToken();

        Request modifiedRequest = getModifiedRequest(originalRequest, accessToken);
        final Response response = chain.proceed(modifiedRequest);
        ResponseBody body = response.body();
        BufferedSource source = body.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = body.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                return response;
            }
        }

        /*try {
            JSONObject json = new JSONObject(buffer.clone().readString(charset));
            if (json.getInt("code") == Constants.USER_LOCATION_INVALID) {
                String mess = "Invalid user location";
                try {
                    JSONArray message = json.getJSONArray("message");
                    mess = message.getJSONObject(0).getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                throw new IlligelUserLocationException(mess);
            } else if ((json.getInt("code") == Constants.INVALID_SIGNATURE)) {
                // clear prefs data in case of invalid signature to logout existing logged in user
                PrefManager.getInstance().logoutUser();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        elapsedTime = (SystemClock.elapsedRealtime() - time) / 1000;

        return response;
    }

    /**
     * Adds security headers to the original request
     */
    private Request getModifiedRequest(Request originalRequest, @Nullable String accessToken) {

        final String enAccessToken
                = (isEmpty(accessToken)) ? "" : accessToken;
        Request.Builder builder = originalRequest.newBuilder();

        if (prefManager.isLoggedin()) {
            builder.addHeader("Authorization", enAccessToken).build();
        }
        return builder.build();


    }
}
