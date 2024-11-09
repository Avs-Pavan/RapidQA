package com.pavan.rapidqa.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavan.rapidqa.domain.Result
import com.pavan.rapidqa.domain.TestGetRequestUseCase
import com.pavan.rapidqa.store.RapidQADataStore
import com.pavan.rapidqa.tracer.RapidQATraceRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val testUseCase: TestGetRequestUseCase,
    private val dataStore: RapidQADataStore<Long, RapidQATraceRecord>
) : ViewModel() {


    val globalExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("TestViewModel", "CoroutineExceptionHandler", throwable)
    }

    private val _uiState = MutableStateFlow(TestUIState())
    val uiState = _uiState
        .onStart {
            getTest()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TestUIState()
        )


    private fun getTest() {
        viewModelScope.launch(globalExceptionHandler) {
            when (val result = testUseCase()) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            testUiModel = TestUIModel(
                                title = result.data.title,
                                body = result.data.body
                            )
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            error = result.error.message ?: "Unknown error"
                        )
                    }
                }
            }
            Log.e("Data store", dataStore.keys().size.toString())
        }
    }

}
