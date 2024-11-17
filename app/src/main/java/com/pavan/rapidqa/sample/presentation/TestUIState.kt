package com.pavan.rapidqa.sample.presentation

data class TestUIState(
    val testUiModel: TestUIModel = TestUIModel(),
    val error: String = ""
)
