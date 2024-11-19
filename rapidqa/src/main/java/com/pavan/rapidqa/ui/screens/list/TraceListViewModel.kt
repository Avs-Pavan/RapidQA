/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/12/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.screens.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavan.rapidqa.store.RapidQADataStore
import com.pavan.rapidqa.tracer.RapidQATraceRecord
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.toString

class TraceListViewModel(
    private val dataStore: RapidQADataStore<Long, RapidQATraceRecord>
) : ViewModel() {

    private val _uiState = MutableStateFlow<TraceListScreenUIState>(TraceListScreenUIState())
    val uiState = _uiState.onStart {
        getTraces()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        TraceListScreenUIState()
    )

    val globalExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "CoroutineExceptionHandler", throwable)
    }

    private val originalTraces = mutableListOf<RapidQATraceRecord>()


    fun onResponseCodeSelected(responseCode: ResponseCode) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedResponseCode = responseCode)
            filterTraces()
        }
    }

    fun onMethodTypeSelected(methodType: MethodType) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedMethodType = methodType)
            filterTraces()
        }
    }

    private fun filterTraces() {
        val currentState = _uiState.value
        val filteredTraces = originalTraces.filter { trace ->
            val responseCodeMatches = when (currentState.selectedResponseCode) {
                ResponseCode.ALL -> true
                ResponseCode.CODE_200 -> trace.responseCode in 200..299
                ResponseCode.CODE_300 -> trace.responseCode in 300..399
                ResponseCode.CODE_400 -> trace.responseCode in 400..499
                ResponseCode.CODE_500 -> trace.responseCode in 500..599
                else -> true
            }
            val methodTypeMatches =
                currentState.selectedMethodType == MethodType.ALL || trace.request.method == currentState.selectedMethodType.method
            responseCodeMatches && methodTypeMatches
        }
        _uiState.value = currentState.copy(traces = filteredTraces)
    }

    private fun getTraces() {
        viewModelScope.launch(globalExceptionHandler) {
            dataStore.getAll().let { traces ->
                originalTraces.clear()
                originalTraces.addAll(traces.entries.sortedBy { it.key }.map { it.value })
                _uiState.update {
                    it.copy(traces = originalTraces)
                }
            }
        }
    }

    companion object {
        private const val TAG = "RapidQa - TraceListViewModel"
    }
}