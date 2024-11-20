/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/18/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.exporter

import android.content.Context
import android.util.Log
import com.pavan.rapidqa.tracer.RapidQATraceRecord
import com.pavan.rapidqa.tracer.RapidQATraceRequest
import com.pavan.rapidqa.ui.components.asTime
import java.io.File
import java.io.FileOutputStream

class RapidQaTextExporter : RapidQaExporter {
    override fun export(
        context: Context,
        traceRecord: RapidQATraceRecord,
        fileName: String,
        description: String
    ): File {
        val newFileName = "RapidQA_Trace_${fileName.trim()}_${
            traceRecord.traceId.asTime().replace(" ", "_")
        }.txt"
        val file = File(context.filesDir, newFileName)
        try {
            FileOutputStream(file).use {
                it.write(traceRecord.toText(description).toByteArray())
            }
            Log.d("RapidQaTextExporter", "Trace record exported to text file: ${file.absolutePath}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("RapidQaTextExporter", "Error exporting trace record to text file", e)
        }
        return file
    }

    fun RapidQATraceRecord.toText(description: String): String {
        return """
/*************************************************
* Author: Pavan, Arepalli
* GitHub: https://github.com/Avs-Pavan/RapidQA
*************************************************/

If you like this project, please give it a star on GitHub and follow me for more updates.
If you encounter any issues, feel free to open an issue on GitHub.

/****************************************************
* Description: ${description.takeIf { it.isNotEmpty() } ?: "N/A"}
****************************************************/

******************** TRACE RECORD *******************

/************************************************
* Export Date: ${System.currentTimeMillis().asTime()}
************************************************/

******************** REQUEST ********************

${request.toText()}

*************************************************

******************** RESPONSE *******************

Response Code: $responseCode
Response Message: ${responseMessage.takeIf { it.isNotEmpty() } ?: "N/A"}
Response Content Type: ${responseContentType.takeIf { it.isNotEmpty() } ?: "N/A"}
Response Content Length: $responseContentLength
Trace ID: $traceId
Total Time: $totalTime
Server Response Time: $serverResponseTime

Response Headers:
${responseHeaders.joinToString("\n") { "  ${it.first}: ${it.second}" }}

Response Body:
${responseBody.takeIf { it.isNotEmpty() } ?: "N/A"}

*************************************************
    """.trimIndent()
    }

    fun RapidQATraceRequest.toText(): String {
        return """
Name: ${name.takeIf { it.isNotEmpty() } ?: "N/A"}
Method: ${method.takeIf { it.isNotEmpty() } ?: "N/A"}
URL: ${url.encodedUrl.takeIf { it.isNotEmpty() } ?: "N/A"}
Content Type: ${contentType.takeIf { it.isNotEmpty() } ?: "N/A"}
Tags:
${tags.joinToString("\n") { "  ${it.takeIf { it.isNotEmpty() } ?: "N/A"}" }}
Time: ${time.asTime()}

Headers:
${headers.joinToString("\n") { "  ${it.first}: ${it.second}" }}

Body:
${body.takeIf { !it.isNullOrEmpty() } ?: "N/A"}
    """.trimIndent()
    }
}


