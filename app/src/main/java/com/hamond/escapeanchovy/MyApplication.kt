package com.hamond.escapeanchovy

import android.app.Application
import com.google.firebase.FirebaseApp
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        NaverIdLoginSDK.initialize(this, BuildConfig.NAVER_CLIENT_ID, BuildConfig.NAVER_CLIENT_SECRET, this.getString(R.string.app_name))
    }
}