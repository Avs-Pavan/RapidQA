package com.pavan.rapidqa.mocker




@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Mocked(
    val responseCode: Int = 200,
    val fileName: String = ""
)