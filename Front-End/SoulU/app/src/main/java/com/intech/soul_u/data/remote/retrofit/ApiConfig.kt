package com.intech.soul_u.data.remote.retrofit

import com.intech.soul_u.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{

        private const val HEADER_AUTHORIZATION = "Authorization"
        fun getApiService(token: String): ApiService{
            val loggingInterceptor = if(BuildConfig.DEBUG) { HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY) }else { HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.NONE) }
            val authInterceptor = Interceptor {chain ->
                val req = chain.request()
                val requestHeaders = req.newBuilder()
                    .addHeader(HEADER_AUTHORIZATION, token)
                    .build()
                chain.proceed(requestHeaders)
            }
            println("ApiConfig, token : $token")
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}