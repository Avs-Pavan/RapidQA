/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/19/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.exporter

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.pavan.rapidqa.tracer.RapidQATraceRecord
import com.pavan.rapidqa.ui.components.asTime
import java.io.File
import java.io.FileOutputStream


class RapidQaPostmanExporter : RapidQaExporter {
    override fun export(
        context: Context,
        traceRecord: RapidQATraceRecord,
        fileName: String,
        description: String
    ): File {

        val newFileName = "RapidQA_Trace_${fileName.trim()}_${
            traceRecord.traceId.asTime().replace(" ", "_")
        }.json"
        val dir = context.filesDir
        dir.listFiles { file -> file.name.startsWith("RapidQA") }?.forEach { it.delete() }

        val file = File(context.filesDir, newFileName)
        try {
            FileOutputStream(file).use {
                it.write(traceRecord.toPostmanJson(fileName, description).toByteArray())
            }
            Log.d(
                "PostmanToOpenApiExporter",
                "Trace record exported to OpenAPI JSON file: ${file.absolutePath}"
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(
                "PostmanToOpenApiExporter",
                "Error exporting trace record to OpenAPI JSON file",
                e
            )
        }
        return file
    }

    private fun RapidQATraceRecord.toPostmanJson(fileName: String, description: String): String {
        val requestHeaders = request.headers.toMutableList()
        if (request.contentType.isNotEmpty()) {
            requestHeaders.add("Content-Type" to request.contentType)
        }
        val formattedRequestHeaders = requestHeaders.map { header ->
            mapOf("key" to header.first, "value" to header.second)
        }

        val responseHeaders = responseHeaders.toMutableList()
        if (responseContentType.isNotEmpty()) {
            responseHeaders.add("Content-Type" to responseContentType)
        }
        val formattedResponseHeaders = responseHeaders.map { header ->
            mapOf("key" to header.first, "value" to header.second)
        }
        val requestBody = request.body?.takeIf { it.isNotEmpty() }?.let {
            mapOf("mode" to "raw", "raw" to it)
        }

        val postmanCollection = mapOf(
            "info" to mapOf(
                "name" to "RapidQA_Trace_$fileName",
                "_postman_id" to traceId.toString(),
                "schema" to "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
            ),
            "item" to listOf(
                mapOf(
                    "name" to request.name,
                    "request" to mapOf(
                        "method" to request.method,
                        "header" to formattedRequestHeaders,
                        "body" to requestBody,
                        "url" to mapOf(
                            "raw" to request.url.encodedUrl,
                            "protocol" to request.url.scheme,
                            "host" to request.url.host.split("."),
                            "path" to request.url.path.split("/")
                        ),
                        "description" to createSign(description)
                    ),
                    "response" to listOf(
                        mapOf(
                            "name" to "Mocked Response",
                            "originalRequest" to mapOf(
                                "method" to request.method,
                                "header" to formattedRequestHeaders,
                                "body" to mapOf(
                                    "mode" to "raw",
                                    "raw" to (request.body ?: "N/A")
                                ),
                                "url" to mapOf(
                                    "raw" to request.url.encodedUrl,
                                    "protocol" to request.url.scheme,
                                    "host" to request.url.host.split("."),
                                    "path" to request.url.path.split("/")
                                )
                            ),
                            "status" to "OK",
                            "code" to responseCode,
                            "header" to formattedResponseHeaders,
                            "body" to responseBody
                        )
                    )
                )
            )
        )

        return Gson().toJson(postmanCollection)
    }


    private fun createSign(description: String): String {
        return """
        ## Description:
        ${description.takeIf { it.isNotEmpty() } ?: "N/A"}
        
        ### Exported time:
        ${System.currentTimeMillis().asTime()}
        
        ### ❤️ RapidQA by :
        Pavan, Arepalli  <br>
        GitHub: [https://github.com/Avs-Pavan/RapidQA](https://github.com/Avs-Pavan/RapidQA)
    """.trimIndent()
    }

}