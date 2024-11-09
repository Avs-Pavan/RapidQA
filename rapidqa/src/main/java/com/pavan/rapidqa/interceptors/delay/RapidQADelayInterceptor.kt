package com.pavan.rapidqa.interceptors.delay

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class RapidQADelayInterceptor(
    private val isDelayEnabled: () -> Boolean = { false },
    private val includeHeaders: Boolean = false
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

            Thread.sleep(annotation.timeMills)

            if (includeHeaders) {
                val newRequest = initialRequest.newBuilder()
                    .addHeader(DELAY_INTERCEPTOR_HEADER, "true")
                    .addHeader(DELAY_INTERCEPTOR_HEADER_DELAY_TIME, annotation.timeMills.toString())
                    .build()
                return chain.proceed(newRequest)
            }

            return chain.proceed(initialRequest)

        } catch (e: Exception) {
            Log.e(DELAY_INTERCEPTOR_TAG, "Error in delay interceptor: ", e)
            throw e
        }
    }

    companion object {
        const val DELAY_INTERCEPTOR_TAG = "RapidQA-DelayInterceptor"
        const val DELAY_INTERCEPTOR_HEADER = "RapidQA-Delay"
        const val DELAY_INTERCEPTOR_HEADER_DELAY_TIME = "RapidQA-Delay-Time"
    }

}