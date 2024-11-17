package com.pavan.rapidqa.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pavan.rapidqa.sample.presentation.TestViewModel
import com.pavan.rapidqa.sample.presentation.ui.theme.RapidQASampleTheme
import com.pavan.rapidqa.store.RapidQADataStore
import com.pavan.rapidqa.tracer.RapidQATraceRecord
import com.pavan.rapidqa.ui.RapidQATracerActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStore: RapidQADataStore<Long, RapidQATraceRecord>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RapidQASampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                startActivity(
                                    RapidQATracerActivity.getInstance(
                                        context = this@MainActivity,
                                        dataStore = dataStore
                                    )
                                )
                            }
                        ) {
                            Icon(Icons.Default.Settings, contentDescription = "Open Tracer")
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TestApi()
                    }
                }
            }
        }
    }
}

@Composable
private fun TestApi(
    testViewModel: TestViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val testUiState by testViewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = testUiState.testUiModel.title)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = testUiState.testUiModel.body)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = testUiState.error, color = Color.Red)
    }
}