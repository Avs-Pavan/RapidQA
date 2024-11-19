/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/12/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.screens.detail

import android.content.Context
import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults.titleContentColor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pavan.rapidqa.R
import com.pavan.rapidqa.ui.components.RapidQAResponseCardUI
import com.pavan.rapidqa.ui.components.asTime
import com.pavan.rapidqa.ui.theme.Teal40
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TraceDetailScreenUI(
    traceId: Long,
    modifier: Modifier = Modifier,
    viewModel: TraceDetailViewModel
) {


    val context = LocalContext.current

    var showShareDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = traceId) {
        viewModel.getTrace(traceId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Teal40,
                    titleContentColor = titleContentColor,
                    actionIconContentColor = titleContentColor
                ),
                title = {
                    Text(
                        text = "RapidQA Tracer",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                actions = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_share_24),
                        contentDescription = "Share",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
                                showShareDialog = !showShareDialog
                            }
                    )
                },
            )
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
            ) { event ->
                when (event) {
                    is RapidQaExportEvent.Share -> {
                        when (event.rapidQaExportOption) {
                            RapidQaExportOption.TEXT -> {
                                uiState.trace?.let { trace ->
                                    shareTextFile(
                                        context = context,
                                        file = viewModel.textExporter.export(
                                            context = context,
                                            traceRecord = trace,
                                            fileName = "RapidQA_Trace_${event.fileName.trim()}_${
                                                trace.traceId.asTime().replace(" ", "_")
                                            }.txt"
                                        )
                                    )
                                }
                            }

                            RapidQaExportOption.JSON -> {
                                //viewModel.jsonExporter.export(uiState.trace!!, "RapidQA_Trace_${uiState.trace?.traceId}.json")
                            }

                            RapidQaExportOption.OPEN_API -> {
                                //viewModel.htmlExporter.export(uiState.trace!!, "RapidQA_Trace_${uiState.trace?.traceId}.html")
                            }
                        }
                    }
                }
            }

            uiState.trace?.let {
                RapidQAResponseCardUI(response = it, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

fun shareTextFile(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share file using"))
}

