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

                val taggedRequest =
                    initialRequest.newBuilder()
                        .tag(
                            RapidQATagMocked::class.java,
                            RapidQATagMocked(
                                fileName = annotation.fileName,
                                responseCode = annotation.responseCode
                            )
                        )
                        .build()

                return getMockResponse(
                    taggedRequest,
                    annotation.fileName,
                    annotation.responseCode,
                )
            }

            Log.d(
                MOCK_INTERCEPTOR_TAG,
                "No mock annotation found for request: ${initialRequest.url()}"
            )
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
    ): Response {
        Log.d(MOCK_INTERCEPTOR_TAG, "Trying to mock request: ${request.url()} with file: $fileName")
        val jsonString = kotlin.run {
            rapidQAAssetManager.getJsonFromFile(fileName)
        }
        val mockBody = ResponseBody.create(MediaType.parse("application/json"), jsonString)

        return Response.Builder()
            .protocol(Protocol.HTTP_1_1)
            .request(request)
            .code(responseCode)
            .message(mockBody.toString())
            .body(mockBody)
            .build()
    }

    companion object {
        const val MOCK_INTERCEPTOR_TAG = "RapidQA-MockInterceptor"
    }

}