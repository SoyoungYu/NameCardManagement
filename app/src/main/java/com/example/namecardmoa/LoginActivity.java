package com.example.namecardmoa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.SharedPreferencesUtils;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    Context mContext;

    ImageButton login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;

        login_button = findViewById(R.id.login_button);

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {}
                if (throwable != null) {}

                loginCheck();
                return null;
            }
        };

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    LoginClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                } else {
                    LoginClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                }
            }
        });

        loginCheck();
    }

    private void loginCheck() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {

            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null) {
                    Log.i(TAG, "invoke: id=" + user.getId());
                    Log.i(TAG, "invoke: nickname=" + user.getKakaoAccount().getProfile().getNickname());
                    Log.i(TAG, "invoke: profile=" + user.getKakaoAccount().getProfile().getProfileImageUrl());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    String user_nickname = user.getKakaoAccount().getProfile().getNickname();
                    String user_profile = user.getKakaoAccount().getProfile().getProfileImageUrl();

                    SharedPreferenceUtil.setString(mContext, "user_nickname", user_nickname);
                    SharedPreferenceUtil.setString(mContext, "user_profile", user_profile);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                return null;
            }
        });
    }
}