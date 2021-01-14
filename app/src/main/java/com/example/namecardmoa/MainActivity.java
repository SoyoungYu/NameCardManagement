package com.example.namecardmoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {
    Context mContext;

    ImageView profile_imageView;
    TextView nickname_textView;
    Button logout_button;

    String user_nickname, user_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        user_nickname = SharedPreferenceUtil.getString(mContext, "user_nickname");
        user_profile = SharedPreferenceUtil.getString(mContext, "user_profile");

        profile_imageView = findViewById(R.id.profile_imageView);
        nickname_textView = findViewById(R.id.nickname_textView);
        logout_button = findViewById(R.id.logout_button);

        nickname_textView.setText(user_nickname + "님, 반가워요!");
        Glide.with(profile_imageView).load(user_profile).into(profile_imageView);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        SharedPreferenceUtil.clear(mContext);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        finish();
                        
                        return null;
                    }
                });
            }
        });
    }
}