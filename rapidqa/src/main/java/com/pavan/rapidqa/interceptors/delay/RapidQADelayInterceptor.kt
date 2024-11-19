package com.pavan.rapidqa.interceptors.delay

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class RapidQADelayInterceptor(
    private val isDelayEnabled: () -> Boolean = { false },
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val initialRequest = chain.request()
            val isDelayEnabled = isDelayEnabled()

            if (isDelayEnabled.not()) {
                return chain.proceed(initialRequest)
            }

            val method = initialRequest.tag(Invocation::class.java)?.method()
            val annotation = method?.getAnnotation(Delayed::class.java)

            if (annotation == null) {
                return chain.proceed(initialRequest)
            }

            if (annotation.timeMills < 0) {
                throw IllegalArgumentException("Delay time cannot be negative")
            }

            Thread.sleep(annotation.timeMills)

            val newRequest = initialRequest.newBuilder()
                .tag(RapidQATagDelay::class.java, RapidQATagDelay(annotation.timeMills))
                .build()

            return chain.proceed(newRequest)

        } catch (e: Exception) {
            Log.e(DELAY_INTERCEPTOR_TAG, "Error in delay interceptor: ", e)
            throw e
        }
    }

    companion object {
        const val DELAY_INTERCEPTOR_TAG = "RapidQA-DelayInterceptor"
    }

}