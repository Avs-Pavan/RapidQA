package com.pavan.rapidqa.tracer

import okhttp3.Response

data class RapidQATraceRecord(
//    val request: Request,
    val response: Response,
    val traceId: Long
)