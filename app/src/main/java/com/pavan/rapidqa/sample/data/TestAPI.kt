package com.pavan.rapidqa.sample.data


import com.pavan.rapidqa.interceptors.delay.Delayed
import com.pavan.rapidqa.interceptors.tag.Named
import com.pavan.rapidqa.mocker.Mocked
import com.pavan.rapidqa.sample.data.model.TestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface TestAPI {

    @Named(tag = "Get API")
    @Delayed(timeMills = 2000)
//    @Mocked(fileName = "test_400", responseCode = 400)
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer your_token_here"
    )
    @GET("test")
    suspend fun getTest(): Response<TestDTO>

    // Language mocking -
    @Named(tag = "Post API")
    @Mocked(fileName = "test_200", responseCode = 200)
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer your"
    )
    @POST("test")
    suspend fun postTest(@Body testDTO: TestDTO): Response<TestDTO>


}