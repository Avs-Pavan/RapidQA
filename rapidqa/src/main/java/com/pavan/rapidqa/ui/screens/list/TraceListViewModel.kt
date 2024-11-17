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

    private fun getTraces() {
        viewModelScope.launch(globalExceptionHandler) {
            dataStore.getAll().let { traces ->
                _uiState.update {
                    it.copy(traces = traces.entries.sortedBy { it.key }.map { it.value })
                }
            }
        }
    }

    companion object {
        private const val TAG = "RapidQa - TraceListViewModel"
    }
}