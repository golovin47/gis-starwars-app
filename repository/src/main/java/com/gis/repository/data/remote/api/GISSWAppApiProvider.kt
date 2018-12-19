package com.gis.repository.data.remote.api

import com.gis.repository.BuildConfig
import com.github.aurae.retrofit2.LoganSquareConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class GISSWAppApiProvider {

  companion object {

    fun createApi(): GISSWAppApi {
      val client = OkHttpClient.Builder()
        .addInterceptor((Interceptor { chain ->
          val original = chain.request()

          val interceptedRequest = original.newBuilder()
            .header("Content-Type", "application/json")
            .method(original.method(), original.body())
            .build()

          return@Interceptor chain.proceed(interceptedRequest)
        }))
        .addInterceptor(HttpLoggingInterceptor().apply {
          level = if (BuildConfig.DEBUG) BODY else NONE
        })
        .build()

      return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(LoganSquareConverterFactory.create())
        .build()
        .create(GISSWAppApi::class.java)
    }
  }
}