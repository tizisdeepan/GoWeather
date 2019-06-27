package com.deepan.goweather.helpers

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ApiCalls {
    val myClient: OkHttpClient = OkHttpClient.Builder().connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).build()
}