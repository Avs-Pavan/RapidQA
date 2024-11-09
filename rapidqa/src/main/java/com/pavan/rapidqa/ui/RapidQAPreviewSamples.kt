package com.pavan.rapidqa.ui

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

}