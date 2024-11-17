package com.pavan.rapidqa.interceptors.delay


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Delayed(val timeMills: Long = 0)