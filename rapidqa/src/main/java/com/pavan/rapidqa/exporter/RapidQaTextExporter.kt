/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/18/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.exporter

import com.pavan.rapidqa.tracer.RapidQATraceRecord
import com.pavan.rapidqa.tracer.RapidQATraceRequest
import java.io.File

class RapidQaTextExporter : RapidQaExporter {
    override fun export(traceRecord: RapidQATraceRecord, fileName: String): File {
        val file = File(fileName)
        file.writeText(traceRecord.toText())
        return file
    }

    fun RapidQATraceRecord.toText(): String {
        return """
        
        ******************** REQUEST ********************
        
        ${request.toText()}
        
        *************************************************
        
        ******************** RESPONSE *******************
        
        Response Code: $responseCode
        Response Message: $responseMessage
        Response Headers:
        ${responseHeaders.joinToString("\n") { "  ${it.first}: ${it.second}" }}
        Response Body:
        $responseBody
        Response Content Type: $responseContentType
        Response Content Length: $responseContentLength
        Trace ID: $traceId
        Total Time: $totalTime
        Server Response Time: $serverResponseTime
        
        *************************************************
        
        Author: Venkata Sai Pavan, Arepalli
        Email: avspavan1234@gmail.com
        GitHub: https://github.com/Avs-Pavan/RapidQA

        If you like this project, please give it a star on GitHub and follow me for more updates.
        If you encounter any issues, feel free to open an issue on GitHub.
    """.trimIndent()
    }

    fun RapidQATraceRequest.toText(): String {
        return """
        Name: $name
        Method: $method
        URL: ${url.encodedUrl}
        Headers:
        ${headers.joinToString("\n") { "  ${it.first}: ${it.second}" }}
        Body:
        $body
        Content Type: $contentType
        Tags:
        ${tags.joinToString("\n") { "  $it" }}
        Time: $time
    """.trimIndent()
    }
}

