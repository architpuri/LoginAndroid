package com.aptechno.login.login.data.api;

import com.aptechno.login.login.data.model.Login;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AppService {

    @POST("api/users")
    @FormUrlEncoded
    Call<Login> getLoginResponse(@Field("email") String email,
                                 @Field("password") String password);

    @POST("api/users")
    Call<Login> getResponse(@Body RequestBody body);
}
