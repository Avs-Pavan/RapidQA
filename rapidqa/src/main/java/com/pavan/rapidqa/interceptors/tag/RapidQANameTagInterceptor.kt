/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/9/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.interceptors.tag

import android.util.Log
import com.pavan.rapidqa.tag.RapidQATag
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class RapidQANameTagInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val initialRequest = chain.request()

            val method = initialRequest.tag(Invocation::class.java)?.method()
            val annotation = method?.getAnnotation(Named::class.java)

            if (annotation == null) {
                return chain.proceed(initialRequest)
            }

            val newRequest = initialRequest.newBuilder()
                .tag(RapidQATag::class.java, RapidQATagNamed(annotation.tag))
                .build()

            return chain.proceed(newRequest)

        } catch (e: Exception) {
            Log.e(NAME_INTERCEPTOR_TAG, "Error in name tag interceptor: ", e)
            throw e
        }
    }

    companion object {
        const val NAME_INTERCEPTOR_TAG = "RapidQA-NameTagInterceptor"
    }
}