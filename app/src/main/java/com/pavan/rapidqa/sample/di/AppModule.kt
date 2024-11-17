package com.pavan.rapidqa.sample.di

import android.content.Context
import com.pavan.rapidqa.interceptors.delay.RapidQADelayInterceptor
import com.pavan.rapidqa.interceptors.tag.RapidQANameTagInterceptor
import com.pavan.rapidqa.mocker.RapidQAMockInterceptor
import com.pavan.rapidqa.sample.data.TestAPI
import com.pavan.rapidqa.store.RapidQADataStore
import com.pavan.rapidqa.store.RapidQAInMemoryDataStore
import com.pavan.rapidqa.tracer.RapidQATraceRecord
import com.pavan.rapidqa.tracer.RapidQaTracer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideMockInterceptor(
        @ApplicationContext context: Context
    ): RapidQAMockInterceptor {
        return RapidQAMockInterceptor(context.assets, isGlobalMockingEnabled = { true })
    }

    @Singleton
    @Provides
    fun provideQADataStore(): RapidQADataStore<Long, RapidQATraceRecord> {
        return RapidQAInMemoryDataStore()
    }

    @Singleton
    @Provides
    fun provideQATracer(dataStore: RapidQADataStore<Long, RapidQATraceRecord>): RapidQaTracer {
        return RapidQaTracer(dataStore)
    }

    @Singleton
    @Provides
    fun provideDelayedInterceptor(): RapidQADelayInterceptor {
        return RapidQADelayInterceptor(isDelayEnabled = { true })
    }

    @Singleton
    @Provides
    fun provideNameTagInterceptor(): RapidQANameTagInterceptor {
        return RapidQANameTagInterceptor()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        rapidQAMockInterceptor: RapidQAMockInterceptor,
        rapidQaTracer: RapidQaTracer,
        rapidQADelayInterceptor: RapidQADelayInterceptor,
        rapidQaNameTagInterceptor: RapidQANameTagInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(rapidQaNameTagInterceptor)
            .addInterceptor(rapidQADelayInterceptor)
            .addInterceptor(rapidQaTracer)
            .addInterceptor(rapidQAMockInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://example.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTempApi(
        retrofit: Retrofit
    ): TestAPI {
        return retrofit.create(TestAPI::class.java)
    }

}