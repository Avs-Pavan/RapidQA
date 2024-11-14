package com.pavan.rapidqa.ui

import com.pavan.rapidqa.ui.components.RapidQARequestUIModel
import com.pavan.rapidqa.ui.components.RapidQaURL
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

    val SAMPLE_RAPID_QA_UI_MODEL = RapidQARequestUIModel(
        name = "Sample Get Request",
        method = "GET",
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