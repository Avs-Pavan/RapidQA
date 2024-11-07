package com.pavan.rapidqa.domain

import javax.inject.Inject

class TestGetRequestUseCase @Inject constructor(
    private val ghostRepo: GhostRepo
) {
    suspend operator fun invoke(): Result<TestModel, Exception> {
        return ghostRepo.testGet()
    }
}
