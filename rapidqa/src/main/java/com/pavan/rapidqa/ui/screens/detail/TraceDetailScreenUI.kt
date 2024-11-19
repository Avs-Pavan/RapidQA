/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/12/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.screens.detail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pavan.rapidqa.R
import com.pavan.rapidqa.ui.components.RapidQAResponseCardUI
import com.pavan.rapidqa.ui.theme.Teal40


@Composable
fun TraceDetailScreenUI(
    traceId: Long,
    modifier: Modifier = Modifier,
    viewModel: TraceDetailViewModel
) {

    var showShareDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = traceId) {
        viewModel.getTrace(traceId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
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
                    painter = painterResource(id = R.drawable.baseline_share_24),
                    contentDescription = "Share",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        showShareDialog = !showShareDialog
                    }
                )
            }
        },
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .animateContentSize()
        ) {

            ShareOptionsCardUI(
                isVisible = showShareDialog,
            )

            uiState.trace?.let {
                RapidQAResponseCardUI(response = it, modifier = Modifier.padding(8.dp))
            }
        }
    }
}