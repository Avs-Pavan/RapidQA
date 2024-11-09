package com.pavan.rapidqa.tracer

import android.util.Log
import com.pavan.rapidqa.store.RapidQADataStore
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RapidQaTracer(
    private val dataStore: RapidQADataStore<String, Pair<Request, Response>>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val initialRequest = chain.request()
            val response = chain.proceed(initialRequest)
            dataStore.put(chain.request().url().url().toString(), Pair(initialRequest, response))
            return response
        } catch (e: Exception) {
            Log.e(RAPID_QA_TRACER_TAG, "Exception in intercept:", e)
            throw e
        }
    }
    companion object{
        const val RAPID_QA_TRACER_TAG = "RapidQA-RapidQaTracer"
    }

}