package com.pavan.rapidqa.ui

import com.pavan.rapidqa.tracer.RapidQATraceRecord
import com.pavan.rapidqa.tracer.RapidQATraceRequest
import com.pavan.rapidqa.tracer.RapidQaURL
import com.pavan.rapidqa.tracer.toRapidTraceRequest
import okhttp3.MediaType
import okhttp3.Request.Builder

object RapidQAPreviewSamples {

    val SAMPLE_GET_REQUEST = Builder()
        .url("https://jsonplaceholder.typicode.com/posts/1")
        .get()
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .header("Authorization", "Bearer 123456")
        .tag("Delayed: 1000ms")
        .tag("Mocked: 200: sample.json")
        .build()

    val SAMPLE_RESPONSE = RapidQATraceRecord(
        traceId = 1,
        totalTime = 1000,
        serverResponseTime = 500,
        request = SAMPLE_GET_REQUEST.toRapidTraceRequest(),
        responseCode = 200,
        responseMessage = "OK",
        responseHeaders = SAMPLE_GET_REQUEST.headers()
            .toMultimap().flatMap { it.value.map { value -> it.key to value } },
        responseBody = "{\n  \"userId\": 1,\n  \"id\": 1,\n  \"title\": \"Sample Title\",\n  \"body\": \"Sample Body\"\n}",
        responseContentType = MediaType.get("application/json").toString(),
        responseContentLength = 100
    )

    val SAMPLE_RAPID_QA_UI_MODEL = RapidQATraceRequest(
        name = "Sample Get Request",
        method = "PATCH",
        url = RapidQaURL(
            scheme = "https",
            host = "jsonplaceholder.typicode.com",
            port = 443,
            path = "/posts/1",
            queryParams = listOf(Pair("postId", "1"), Pair("userId", "1")),
            encodedUrl = "https://jsonplaceholder.typicode.com/posts/1"
        ),
        headers = SAMPLE_GET_REQUEST.headers()
            .toMultimap().entries.map { it.key to it.value.joinToString() },
        body = "Some body",
        tags = listOf("Mocked: 200: sample.json", "Delayed: 1000ms", "SomeTag")
    )

}