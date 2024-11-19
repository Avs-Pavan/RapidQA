/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/12/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.screens.list

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pavan.rapidqa.R
import com.pavan.rapidqa.ui.components.RapidQAResponseCardMinUI
import com.pavan.rapidqa.ui.theme.Teal40

@Composable
fun TraceListScreenUI(
    viewModel: TraceListViewModel,
    modifier: Modifier = Modifier,
    onTraceClick: (Long) -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .background(Teal40)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(top = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom

            ) {
                Text(
                    text = "RapidQA Tracer",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )

                Icon(
                    painter = painterResource(id = R.drawable.baseline_filter_list_24),
                    contentDescription = "Filters",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        viewModel.onFilterClicked()
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .animateContentSize()
        ) {
            FilterHeader(
                selectedResponseCode = uiState.selectedResponseCode,
                onResponseCodeSelected = { viewModel.onResponseCodeSelected(it) },
                selectedMethodType = uiState.selectedMethodType,
                onMethodTypeSelected = { viewModel.onMethodTypeSelected(it) },
                showFilters = uiState.showFilters
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                items(uiState.traces) { trace ->
                    RapidQAResponseCardMinUI(response = trace, modifier = Modifier.clickable {
                        onTraceClick(trace.traceId)
                    })
                    HorizontalDivider()
                }
            }
        }
    }
}