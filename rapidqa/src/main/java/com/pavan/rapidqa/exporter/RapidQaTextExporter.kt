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
    override fun export(context: Context, traceRecord: RapidQATraceRecord, fileName: String): File {
        val file = File(context.filesDir, fileName)
        try {
            FileOutputStream(file).use {
                it.write(traceRecord.toText().toByteArray())
            }
            Log.d("RapidQaTextExporter", "Trace record exported to text file: ${file.absolutePath}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("RapidQaTextExporter", "Error exporting trace record to text file", e)
        }
        return file
    }

    fun RapidQATraceRecord.toText(): String {
        return """
/*************************************************
* Author: Venkata Sai Pavan, Arepalli
* Email: avspavan1234@gmail.com
* GitHub: https://github.com/Avs-Pavan/RapidQA
*************************************************/

If you like this project, please give it a star on GitHub and follow me for more updates.
If you encounter any issues, feel free to open an issue on GitHub.

******************** REQUEST ********************

${request.toText()}

*************************************************

******************** RESPONSE *******************

Response Code: ${responseCode ?: "N/A"}
Response Message: ${responseMessage ?: "N/A"}
Response Content Type: ${responseContentType ?: "N/A"}
Response Content Length: ${responseContentLength ?: "N/A"}
Trace ID: ${traceId ?: "N/A"}
Total Time: ${totalTime ?: "N/A"}
Server Response Time: ${serverResponseTime ?: "N/A"}

Response Headers:
${responseHeaders.joinToString("\n") { "  ${it.first}: ${it.second}" }}

Response Body:
${responseBody ?: "N/A"}

*************************************************
        """.trimIndent()
    }

    fun RapidQATraceRequest.toText(): String {
        return """
Name: ${name ?: "N/A"}
Method: ${method ?: "N/A"}
URL: ${url.encodedUrl ?: "N/A"}
Content Type: ${contentType ?: "N/A"}
Tags:
${tags.joinToString("\n") { "  $it" }}
Time: ${time.asTime() ?: "N/A"}

Headers:
${headers.joinToString("\n") { "  ${it.first}: ${it.second}" }}

Body:
${body ?: "N/A"}
        """.trimIndent()
    }
}


