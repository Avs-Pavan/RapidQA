package com.pavan.rapidqa.mocker

import android.content.res.AssetManager
import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Invocation

class RapidQAMockInterceptor(
    assetManager: AssetManager,
    private val isGlobalMockingEnabled: () -> Boolean = { true },
    private val includeHeaders: Boolean = false
) : Interceptor {

    private val rapidQAAssetManager = RapidQAAssetManager(assetManager)

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val initialRequest = chain.request()
            val isMockingEnabled = isGlobalMockingEnabled()

            if (isMockingEnabled.not()) {
                return chain.proceed(initialRequest)
            }

            val method = initialRequest.tag(Invocation::class.java)?.method()
            val annotation = method?.getAnnotation(Mocked::class.java)
            if (annotation != null) {
                return getMockResponse(
                    initialRequest,
                    annotation.fileName,
                    annotation.responseCode,
                    includeHeaders
                )
            }

            return chain.proceed(initialRequest)
        } catch (e: Exception) {
            Log.e(MOCK_INTERCEPTOR_TAG, "Error in mock interceptor:", e)
            throw e
        }
    }

    private fun getMockResponse(
        request: Request,
        fileName: String,
        responseCode: Int,
        includeHeaders: Boolean
    ): Response {
        Log.d(MOCK_INTERCEPTOR_TAG, "Trying to mock request: ${request.url()} with file: $fileName")
        val jsonString = kotlin.run {
            rapidQAAssetManager.getJsonFromFile(fileName)
        }
        val mockBody = ResponseBody.create(MediaType.parse("application/json"), jsonString)

        val newRequest = if (includeHeaders) {
            request.newBuilder()
                .addHeader(MOCK_INTERCEPTOR_HEADER, "true")
                .addHeader(MOCK_INTERCEPTOR_HEADER_FILE_NAME, fileName)
                .build()

        } else request

        return Response.Builder()
            .protocol(Protocol.HTTP_1_1)
            .request(newRequest)
            .code(responseCode)
            .message(mockBody.toString())
            .body(mockBody)
            .build()
    }

    companion object {
        const val MOCK_INTERCEPTOR_TAG = "RapidQA-MockInterceptor"
        const val MOCK_INTERCEPTOR_HEADER = "RapidQA-Mocked"
        const val MOCK_INTERCEPTOR_HEADER_FILE_NAME = "RapidQA-Mocked-File-Name"
    }

}