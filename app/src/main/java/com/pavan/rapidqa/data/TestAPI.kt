package com.pavan.rapidqa.data

import com.pavan.rapidqa.data.model.TestDTO
import com.pavan.rapidqa.interceptors.delay.Delayed
import com.pavan.rapidqa.interceptors.tag.Named
import retrofit2.Response
import retrofit2.http.GET


interface TestAPI {

    @Named(tag = "Test API")
    @Delayed(timeMills = 2000)
//    @Mocked(fileName = "test_400", responseCode = 400)
    @GET("test")
    suspend fun getTest(): Response<TestDTO>


}