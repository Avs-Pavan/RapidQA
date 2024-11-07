package com.pavan.rapidqa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pavan.rapidqa.presentation.TestViewModel
import com.pavan.rapidqa.ui.theme.RapidQATheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RapidQATheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TestApi(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
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
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = testUiState.testUiModel.title)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = testUiState.testUiModel.body)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = testUiState.error, color = androidx.compose.ui.graphics.Color.Red)
    }
}