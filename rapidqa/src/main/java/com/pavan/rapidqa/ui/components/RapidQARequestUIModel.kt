/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/11/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.components

import androidx.compose.runtime.Immutable
import com.pavan.rapidqa.interceptors.delay.RapidQATagDelay
import com.pavan.rapidqa.interceptors.tag.RapidQATagNamed
import com.pavan.rapidqa.mocker.RapidQATagMocked
import okhttp3.Request

@Immutable
data class RapidQARequestUIModel(
    val name: String,
    val method: String,
    val url: RapidQaURL,
    val headers: List<Pair<String, String>>,
    val body: String,
    val tags: List<String>,
    val time: Long = System.currentTimeMillis()
)


fun Request.toUIModel(): RapidQARequestUIModel {
    return RapidQARequestUIModel(
        name = this.toName(),
        method = this.method(),
        url = this.toRapidQaURL(),
        headers = this.toHeaderList(),
        body = this.body().toString(),
        tags = this.toTags(),
    )
}

@Immutable
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
    val namedTag = this.tag(RapidQATagNamed::class.java)
    if (namedTag != null) {
        tags.add(namedTag.name)
    }
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
