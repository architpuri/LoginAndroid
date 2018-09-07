package com.aptechno.login.login.data.api;

import android.support.annotation.NonNull;

import com.aptechno.login.login.data.local.PrefManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static AppService mAppService = null;

    public static final String mBaseUrl = "http://192.168.43.21:8080/";

    private ApiClient() {
        // default constructor needs to be private otherwise it will expose the behavior of singleton
    }

    public static AppService getInstance(){
        if(mAppService == null) {
            final OkHttpClient okHttpClient = makeOkHttpClient();
            final Retrofit client = makeRetrofit(okHttpClient);
            mAppService  = client.create(AppService.class);
        }
        return mAppService;
    }

    @NonNull
    private static Retrofit makeRetrofit(OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @NonNull
    private static OkHttpClient makeOkHttpClient() {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //add the interceptor for logging the curl commands
        return new OkHttpClient.Builder()
                /*.addInterceptor(new SecurityInterceptor(PrefManager.getInstance()))*/
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new CurlLoggingInterceptor())
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(90,TimeUnit.SECONDS)
                .cache(null)
                .build();
    }
}
