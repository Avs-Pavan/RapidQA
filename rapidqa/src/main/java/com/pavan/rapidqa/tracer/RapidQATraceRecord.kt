package com.pavan.rapidqa.tracer

import com.pavan.rapidqa.interceptors.delay.RapidQATagDelay
import com.pavan.rapidqa.interceptors.tag.RapidQATagNamed
import com.pavan.rapidqa.mocker.RapidQATagMocked
import okhttp3.Request
import okhttp3.RequestBody
import okio.Buffer

data class RapidQATraceRecord(
    val responseCode: Int,
    val responseMessage: String,
    val responseHeaders: List<Pair<String, String>>,
    val responseBody: String,
    val responseContentType: String,
    val responseContentLength: Long,
    val traceId: Long,
    val totalTime: Long,
    val serverResponseTime: Long,
    val request: RapidQATraceRequest,
)

data class RapidQATraceRequest(
    val name: String,
    val method: String,
    val url: RapidQaURL,
    val headers: List<Pair<String, String>>,
    val body: String?,
    val contentType: String = "",
    val tags: List<String>,
    val time: Long = System.currentTimeMillis()
)


fun Request.toRapidTraceRequest(): RapidQATraceRequest {
    return RapidQATraceRequest(
        name = this.toName(),
        method = this.method(),
        url = this.toRapidQaURL(),
        headers = this.toHeaderList(),
        body = this.body()?.asString(),
        contentType = this.body()?.contentType()?.toString() ?: "",
        tags = this.toTags(),
    )
}

fun RequestBody.asString(): String {
    val buffer = Buffer()
    writeTo(buffer)
    return buffer.readUtf8()
}

data class RapidQaURL(
    val scheme: String,
    val host: String,
    val port: Int,
    val path: String,
    val queryParams: List<Pair<String, String>> = emptyList(),
    val encodedUrl: String,
)


fun Request.toName(): String {
    return this.tag(RapidQATagNamed::class.java)?.name ?: ""
}

fun Request.toTags(): List<String> {
    val tags = mutableListOf<String>()
//    val namedTag = this.tag(RapidQATagNamed::class.java)
//    if (namedTag != null) {
//        tags.add(namedTag.name)
//    }
    val mockedTag = this.tag(RapidQATagMocked::class.java)
    if (mockedTag != null) {
        tags.add("Mocked: ${mockedTag.responseCode}: ${mockedTag.fileName}")
    }
    val delayTag = this.tag(RapidQATagDelay::class.java)
    if (delayTag != null) {
        tags.add("Delayed: ${delayTag.timeMills}ms")
    }
    return tags
}


fun Request.toHeaderList(): List<Pair<String, String>> {
    return this.headers().toMultimap().entries.map { it.key to it.value.joinToString() }
}

fun Request.toRapidQaURL(): RapidQaURL {
    return RapidQaURL(
        scheme = this.url().scheme(),
        host = this.url().host(),
        port = this.url().port(),
        path = this.url().encodedPath(),
        queryParams = this.url().queryParameterNames().map {
            Pair(it, this.url().queryParameter(it) ?: "")
        },
        encodedUrl = this.url().toString(),
    )
}

