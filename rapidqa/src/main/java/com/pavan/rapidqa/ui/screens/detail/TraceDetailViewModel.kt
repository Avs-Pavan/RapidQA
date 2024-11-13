/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/12/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.screens.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import com.pavan.rapidqa.store.RapidQADataStore
import com.pavan.rapidqa.tracer.RapidQATraceRecord
import kotlinx.coroutines.CoroutineExceptionHandler

class TraceDetailViewModel constructor(
    private val dataStore: RapidQADataStore<Long, RapidQATraceRecord>
) : ViewModel() {

    val globalExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "CoroutineExceptionHandler", throwable)
    }


    companion object {
        private const val TAG = "RapidQa - TraceDetailViewModel"
    }
}