package com.pavan.rapidqa.tracer

import okhttp3.Request
import okhttp3.Response

data class RapidQATraceRecord(
    val request: Request,
    val response: Response
)