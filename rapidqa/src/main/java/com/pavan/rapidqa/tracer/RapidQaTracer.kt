package com.pavan.rapidqa.tracer

import android.util.Log
import com.pavan.rapidqa.store.RapidQADataStore
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class RapidQaTracer(
    private val dataStore: RapidQADataStore<Long, RapidQATraceRecord>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val initialRequest = chain.request()
            val traceID = System.currentTimeMillis()

            val startNs = System.nanoTime()
            val response = chain.proceed(initialRequest)
            Log.d(RAPID_QA_TRACER_TAG, "Response: ${response.peekBody(Long.MAX_VALUE).string()}")
            val endNs = System.nanoTime()

            dataStore.put(
                traceID,
                RapidQATraceRecord(
                    traceId = traceID,
                    totalTime = TimeUnit.NANOSECONDS.toMillis(endNs - startNs),
                    serverResponseTime = response.receivedResponseAtMillis() - response.sentRequestAtMillis(),
                    request = response.request().toRapidTraceRequest(),
                    responseCode = response.code(),
                    responseMessage = response.message(),
                    responseHeaders = response.headers().toMultimap()
                        .flatMap { it.value.map { value -> it.key to value } },
                    responseBody = response.peekBody(Long.MAX_VALUE).string(),
                    responseContentType = response.body()?.contentType()?.toString() ?: "",
                    responseContentLength = response.body()?.contentLength() ?: 0
                )
            )
            return response
        } catch (e: Exception) {
            Log.e(RAPID_QA_TRACER_TAG, "Exception in intercept:", e)
            throw e
        }
    }

    companion object {
        const val RAPID_QA_TRACER_TAG = "RapidQA-RapidQaTracer"
    }

}