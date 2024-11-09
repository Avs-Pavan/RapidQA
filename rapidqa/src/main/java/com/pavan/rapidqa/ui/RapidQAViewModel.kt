package com.pavan.rapidqa.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.pavan.rapidqa.store.RapidQADataStore
import com.pavan.rapidqa.tracer.RapidQATraceRecord
import kotlinx.coroutines.CoroutineExceptionHandler

class RapidQAViewModel constructor(
    private val dataStore: RapidQADataStore<Long, RapidQATraceRecord>
) : ViewModel() {

    val globalExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "CoroutineExceptionHandler", throwable)
    }


    companion object {
        private const val TAG = "RapidQAViewModel"
    }
}