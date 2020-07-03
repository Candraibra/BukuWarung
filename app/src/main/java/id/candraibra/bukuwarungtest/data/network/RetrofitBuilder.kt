package id.candraibra.bukuwarungtest.data.network

import id.candraibra.bukuwarungtest.BuildConfig
import id.candraibra.bukuwarungtest.BuildConfig.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val getRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val client: OkHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) BODY else NONE
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    val getApi: ApiService by lazy {
        getRetrofit.create(ApiService::class.java)
    }
}