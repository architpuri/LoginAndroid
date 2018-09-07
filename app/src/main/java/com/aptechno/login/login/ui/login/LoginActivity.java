package com.aptechno.login.login.ui.login;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.aptechno.login.login.R;
import com.aptechno.login.login.data.api.ApiClient;
import com.aptechno.login.login.data.local.PrefManager;
import com.aptechno.login.login.data.model.Login;
import com.aptechno.login.login.ui.form.FormActivity;
import com.aptechno.login.login.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aptechno.login.login.util.CommonUtils.isSuccess;
import static com.aptechno.login.login.util.CommonUtils.showToast;

public class LoginActivity extends AppCompatActivity {
        @BindView(R.id.edt_user_id)
        TextInputEditText edtUserEmail;
        @BindView(R.id.edt_user_password)
        TextInputEditText edtUserPassword;
        @BindView(R.id.btn_signin)
        Button btnSignIn;
        @BindView(R.id.textLayout_password)
        TextInputLayout textLayoutPassword;
        @BindView(R.id.textLayout_user)
        TextInputLayout textLayoutuserid;
        private static final String TAG = "LoginActivity";
        private int responseCode = 0;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                edtUserPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().
                        getDrawable(R.drawable.ic_lock, null), null, null, null);
            }
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = edtUserEmail.getText().toString().trim();
                    String password = edtUserPassword.getText().toString().trim();
                    if (TextUtils.isEmpty(email)) {
                        CommonUtils
                                .wrongInputErrorMsg(textLayoutuserid, "Please enter email", edtUserEmail);
                    } else if (!CommonUtils.isValidEmail(email)) {
                        CommonUtils
                                .wrongInputErrorMsg(textLayoutuserid, "Please check email format", edtUserEmail);
                    } else if (TextUtils.isEmpty(password)) {
                        CommonUtils
                                .wrongInputErrorMsg(textLayoutPassword, "Please enter password", edtUserPassword);
                    } else if (!CommonUtils.isValidPassword(password)) {
                        CommonUtils
                                .wrongInputErrorMsg(textLayoutPassword, "Please check password", edtUserPassword);
                    } else {
                        //Allow Login
                        loginCheck(email, password);
                        loginTrial(email, password);
                    }
                }
            });
        }

        public void loginCheck(String email, String password) {
            ApiClient.getInstance().getLoginResponse(email, password)
                    .enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            Login login = response.body();
                            if (isSuccess()) {
                                startActivity(new Intent(LoginActivity.this, FormActivity.class));
                                finish();
                            } else {
                                showToast(LoginActivity.this, "Error ");
                            }
                        }
                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            showToast(LoginActivity.this, "Check Internet Connection");
                        }
                    });

        }
        public void loginTrial(String email, String password){
            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), email);
            ApiClient.getInstance().getResponse(body)
                    .enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            startActivity(new Intent(LoginActivity.this, FormActivity.class));
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            showToast(LoginActivity.this, "Check Internet Connection");
                        }
                    });
        }


    }
