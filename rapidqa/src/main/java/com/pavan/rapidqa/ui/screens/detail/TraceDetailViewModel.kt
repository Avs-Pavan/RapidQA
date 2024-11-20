/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/12/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.screens.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavan.rapidqa.exporter.RapidQaPostmanExporter
import com.pavan.rapidqa.exporter.RapidQaTextExporter
import com.pavan.rapidqa.store.RapidQADataStore
import com.pavan.rapidqa.tracer.RapidQATraceRecord
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TraceDetailViewModel constructor(
    private val dataStore: RapidQADataStore<Long, RapidQATraceRecord>
) : ViewModel() {

    val textExporter by lazy { RapidQaTextExporter() }

    val openApiExporter by lazy { RapidQaPostmanExporter() }

    private val _uiState = MutableStateFlow<TraceDetailScreenUIState>(TraceDetailScreenUIState())
    val uiState = _uiState.asStateFlow()

    val globalExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "CoroutineExceptionHandler", throwable)
    }

    lateinit var traceRecord: RapidQATraceRecord

    fun getTrace(traceId: Long) {
        viewModelScope.launch(globalExceptionHandler) {
            dataStore.get(traceId)?.let { trace ->
                traceRecord = trace
                _uiState.update {
                    TraceDetailScreenUIState(trace)
                }
            }
        }
    }


    companion object {
        private const val TAG = "RapidQa - TraceDetailViewModel"
    }
}