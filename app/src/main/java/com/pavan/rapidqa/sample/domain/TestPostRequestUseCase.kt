package com.pavan.rapidqa.sample.domain

import javax.inject.Inject

class TestPostRequestUseCase @Inject constructor(
    private val ghostRepo: GhostRepo
) {
    suspend operator fun invoke(): Result<TestModel, Exception> {
        return ghostRepo.testPost(TestModel("Some tile ", "Some body - description"))
    }
}
