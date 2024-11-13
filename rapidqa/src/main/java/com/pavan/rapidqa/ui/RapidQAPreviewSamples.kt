package com.pavan.rapidqa.ui

import com.pavan.rapidqa.ui.components.RapidQARequestUIModel
import okhttp3.Request.Builder

object RapidQAPreviewSamples {

    val SAMPLE_GET_REQUEST = Builder()
        .url("https://jsonplaceholder.typicode.com/posts/1")
        .get()
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .header("Authorization", "Bearer 123456")
        .tag("SampleGetRequest")
        .tag("SampleGetRequestTag")
        .build()

    val SAMPLE_RAPID_QA_UI_MODEL = RapidQARequestUIModel(
        name = "Sample Get Request",
        method = "GET",
        url = SAMPLE_GET_REQUEST.url(),
        headers = SAMPLE_GET_REQUEST.headers().toMultimap().entries.map { it.key to it.value.joinToString() },
        body = "Some body",
        tags = SAMPLE_GET_REQUEST.tag().toString().split(", ")
    )

}