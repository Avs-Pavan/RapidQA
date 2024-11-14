package com.pavan.rapidqa.domain

interface GhostRepo {
    suspend fun testGet(): Result<TestModel, Exception>
    suspend fun testPost(testModel: TestModel): Result<TestModel, Exception>
}