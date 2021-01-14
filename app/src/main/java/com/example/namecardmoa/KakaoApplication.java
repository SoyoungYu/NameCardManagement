package com.example.namecardmoa;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, "8d07ffa8592a6af828d5f42c7060a6c8");
    }
}