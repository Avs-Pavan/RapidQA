package com.pavan.rapidqa.sample.domain

import javax.inject.Inject

class TestGetRequestUseCase @Inject constructor(
    private val ghostRepo: GhostRepo
) {
    suspend operator fun invoke(): Result<TestModel, Exception> {
        ghostRepo.testPost(TestModel("title", "body"))
        return ghostRepo.testGet()
    }
}
