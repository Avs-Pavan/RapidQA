package com.pavan.rapidqa.tracer

import android.util.Log
import com.pavan.rapidqa.store.RapidQADataStore
import okhttp3.Interceptor
import okhttp3.Response

class RapidQaTracer(
    private val dataStore: RapidQADataStore<Long, RapidQATraceRecord>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val initialRequest = chain.request()
            val response = chain.proceed(initialRequest)
            dataStore.put(
                System.currentTimeMillis(),
                RapidQATraceRecord(request = initialRequest, response = response)
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