package com.pavan.rapidqa.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pavan.rapidqa.store.RapidQADataStore
import com.pavan.rapidqa.tracer.RapidQATraceRecord
import com.pavan.rapidqa.ui.navigation.RapidQANavHost
import com.pavan.rapidqa.ui.theme.RapidQATheme
import com.pavan.rapidqa.ui.theme.Teal40

class RapidQATracerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RapidQATheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Row(
                            modifier = Modifier
                                .background(Teal40)
                                .fillMaxWidth()
                                .padding(16.dp)
                                .padding(top = 32.dp),
                        ) {
                            Text(
                                text = "RapidQA Tracer",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                ) { innerPadding ->
                    dataStore?.let { ds ->
                        RapidQANavHost(
                            dataStore = ds,
                            modifier = Modifier.padding(innerPadding)
                        )
                    } ?: run {
                        Text(
                            text = "DataStore is null",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }

    companion object {

        const val TAG = "RapidQATracerActivity"

        private var dataStore: RapidQADataStore<Long, RapidQATraceRecord>? = null

        fun getInstance(
            context: Context,
            dataStore: RapidQADataStore<Long, RapidQATraceRecord>
        ): Intent {
            Log.d(TAG, "getInstance")
            this.dataStore = dataStore
            return Intent(context, RapidQATracerActivity::class.java)
        }
    }
}