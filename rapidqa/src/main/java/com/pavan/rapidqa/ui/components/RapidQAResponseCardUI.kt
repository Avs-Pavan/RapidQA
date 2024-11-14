/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/13/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pavan.rapidqa.tracer.RapidQATraceRecord
import com.pavan.rapidqa.ui.RapidQAPreviewSamples
import com.pavan.rapidqa.ui.theme.bold


@Composable
fun RapidQAResponseCardMinUI(
    response: RapidQATraceRecord,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        HttpStatusCodeUI(
            response.responseCode, modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        )
        RapidQARequestCardMinUI(
            request = response.request,
            modifier = Modifier
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RapidQAResponseCardUI(
    response: RapidQATraceRecord,
    modifier: Modifier = Modifier
) {
    val headers by remember(response.responseHeaders) {
        derivedStateOf {
            response.responseHeaders.map { it.first to it.second }
        }
    }
    Box(modifier = modifier) {
        HttpStatusCodeUI(
            response.responseCode, modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        )
        Column {
            RapidQARequestCardUI(
                request = response.request,
            )
            Column(modifier = Modifier.padding(8.dp)) {

                if (headers.isNotEmpty()) {
                    Text(
                        text = "Response Headers:",
                        style = MaterialTheme.typography.labelMedium.bold(),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    RapidQAKeyValueListUI(
                        keyValues = headers,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                if (response.responseBody.isNotEmpty()) {
                    Text(
                        text = "Response Body: ${
                            response.responseContentType
                        } ${response.responseBody.sizeInBytes().toHumanReadableSize()}",
                        style = MaterialTheme.typography.labelMedium.bold(),
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .padding(top = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(Color.LightGray)
                            .padding(1.dp)
                    ) {
                        if (response.responseContentType.contains("json")) {
                            RapidQAJsonViewerUI(
                                jsonString = response.responseBody,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 500.dp)
                                    .background(Color.White)
                                    .padding(8.dp)
                            )
                        } else if (response.responseContentType.contains("html")) {
                            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {

                                Column {
                                    var selectedIndex by remember { mutableIntStateOf(0) }
                                    val options = listOf("Raw", "Preview")
                                    SingleChoiceSegmentedButtonRow(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.Cyan)
                                            .padding(0.dp),
                                    ) {
                                        options.forEachIndexed { index, label ->
                                            SegmentedButton(
                                                border = BorderStroke(0.dp, Color.White),
                                                shape = RectangleShape,
                                                onClick = { selectedIndex = index },
                                                selected = index == selectedIndex
                                            ) { Text(label) }
                                        }
                                    }
                                    if (selectedIndex == 1) {
                                        Text(
                                            text = "Coming soon",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    Color.White
                                                )
                                                .padding(8.dp)
                                        )
//                                        AndroidView(
//                                            factory = { context ->
//                                                WebView(context).apply {
//                                                    loadData(
//                                                        response.responseBody,
//                                                        "text/html",
//                                                        "UTF-8"
//                                                    )
//                                                }
//                                            },
//                                            update = { webView ->
//                                                webView.loadData(
//                                                    response.responseBody,
//                                                    "text/html",
//                                                    "UTF-8"
//                                                )
//                                            },
//                                            modifier = Modifier
//                                                .fillMaxWidth()
//                                                .fillMaxHeight()
//                                        )
                                    } else {
                                        Text(
                                            text = response.responseBody,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.Black,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .heightIn(max = 500.dp)
                                                .background(Color.White)
                                                .padding(8.dp)
                                                .verticalScroll(rememberScrollState())
                                        )
                                    }
                                }
                            }
                        } else {
                            Text(
                                text = response.responseBody,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 500.dp)
                                    .background(Color.White)
                                    .padding(8.dp)
                                    .verticalScroll(rememberScrollState())
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun V1() {
    RapidQAResponseCardMinUI(
        response = RapidQAPreviewSamples.SAMPLE_RESPONSE
    )
}
