package com.bp.twitter.domain.di

import com.bp.twitter.BuildConfig
import com.bp.twitter.data.tweetsearch.repository.service.SearchTwitterApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ServiceDataModule{
    @Provides
    fun provideTwitterServiceApi(retrofit: Retrofit): SearchTwitterApi = retrofit.create(SearchTwitterApi::class.java)

    @Provides
    fun provideClient(): OkHttpClient {

        val consumer = OkHttpOAuthConsumer("", "")
        consumer.setTokenWithSecret("", "")
        val logging = HttpLoggingInterceptor()
        logging.level = (if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        return OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(SigningInterceptor(consumer))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl("https://api.twitter.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
}