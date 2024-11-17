package com.pavan.rapidqa.mocker

import android.content.res.AssetManager
import android.util.Log
import com.pavan.rapidqa.mocker.RapidQAMockInterceptor.Companion.MOCK_INTERCEPTOR_TAG
import java.io.FileNotFoundException

class RapidQAAssetManager(
    private val assetManager: AssetManager
) {
    fun getJsonFromFile(fileName: String): String {
        val jsonFileName = if (fileName.contains(".json")) fileName else "$fileName.json"
        Log.d(MOCK_INTERCEPTOR_TAG, "Final mock file name: $jsonFileName")

        return assetManager.runCatching {
            open(jsonFileName)
                .bufferedReader()
                .use { it.readText() }
        }.getOrElse { error ->
            Log.e(
                MOCK_INTERCEPTOR_TAG,
                "Error reading JSON file $jsonFileName. Check if it exists",
                error
            )
            throw FileNotFoundException("Error reading JSON file $jsonFileName. Check if it exists")
        }
    }
}