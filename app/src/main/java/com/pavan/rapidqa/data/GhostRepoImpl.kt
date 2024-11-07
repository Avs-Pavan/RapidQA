package com.pavan.rapidqa.data

import com.pavan.rapidqa.domain.GhostRepo
import com.pavan.rapidqa.domain.Result
import com.pavan.rapidqa.domain.TestModel
import com.pavan.rapidqa.data.model.TestDTO
import javax.inject.Inject

class GhostRepoImpl @Inject constructor(
    private val testAPI: TestAPI
) : GhostRepo {
    override suspend fun testGet(): Result<TestModel, Exception> {
        val response = testAPI.getTest()
        return if (response.isSuccessful) {
            Result.Success(response.body()?.toDomain() ?: TestModel("", ""))
        } else {
            Result.Error(
                Exception(
                    "Api called failed.\nResponse code: ${response.code()}\n ErrorBody:\n ${
                        response.errorBody()?.string()
                    }"
                )
            )
        }
    }

    private fun TestDTO.toDomain(): TestModel {
        return TestModel(
            title = title,
            body = body
        )
    }
}