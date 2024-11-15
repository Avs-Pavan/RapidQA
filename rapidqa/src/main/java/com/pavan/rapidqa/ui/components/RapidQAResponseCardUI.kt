/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/13/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_DELETE
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_GET
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_HEAD
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_OPTIONS
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_PATCH
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_POST
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_PUT
import com.pavan.rapidqa.ui.theme.Pink80
import com.pavan.rapidqa.ui.theme.Purple80
import com.pavan.rapidqa.ui.theme.TEXT_COLOR_LIGHT
import com.pavan.rapidqa.ui.theme.bold
import com.pavan.rapidqa.ui.theme.italic


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RapidQAResponseCardMinUI(
    response: RapidQATraceRecord,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .paddingFromBaseline(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TagUI(
                tag = response.request.method,
                bgColor = when (response.request.method.uppercase()) {
                    "GET" -> COLOR_HTTP_GET
                    "POST" -> COLOR_HTTP_POST
                    "PUT" -> COLOR_HTTP_PUT
                    "DELETE" -> COLOR_HTTP_DELETE
                    "PATCH" -> COLOR_HTTP_PATCH
                    "HEAD" -> COLOR_HTTP_HEAD
                    else -> COLOR_HTTP_OPTIONS
                },
                textColor = Color.White
            )
            Text(
                modifier = Modifier.weight(1f),
                text = response.request.name,
                style = MaterialTheme.typography.bodyLarge.bold()
            )

            HttpStatusCodeUI(
                response.responseCode
            )
        }

        RapidQAUrlUI(
            url = response.request.url,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        if (response.request.tags.isNotEmpty())
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                response.request.tags.forEach {
                    TagUI(
                        tag = it,
                        bgColor = if (it.contains("Delayed"))
                            Purple80
                        else if (it.contains("Mocked")) Pink80
                        else Color.LightGray,
                        textColor = if (it.contains("Delayed"))
                            Color.White
                        else if (it.contains("Mocked")) Color.White
                        else Color.Black
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }

        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(end = 8.dp),
            text = "Total time : " + response.totalTime.toString() + " ms, Server response time : " + response.serverResponseTime + " ms",
            style = MaterialTheme.typography.labelMedium.bold().italic(),
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                text = "Message: " + if (response.responseMessage.isNotEmpty()) response.responseMessage else "N/A",
                style = MaterialTheme.typography.labelSmall.bold().italic(),
            )

            Text(
                text = response.request.time.asTime(),
                style = MaterialTheme.typography.labelSmall.bold().italic(),
                textAlign = androidx.compose.ui.text.style.TextAlign.End,
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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
    Column(
        modifier = modifier
            .padding(vertical = 8.dp)
            .padding(horizontal = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TagUI(
                tag = response.request.method,
                bgColor = when (response.request.method.uppercase()) {
                    "GET" -> COLOR_HTTP_GET
                    "POST" -> COLOR_HTTP_POST
                    "PUT" -> COLOR_HTTP_PUT
                    "DELETE" -> COLOR_HTTP_DELETE
                    "PATCH" -> COLOR_HTTP_PATCH
                    "HEAD" -> COLOR_HTTP_HEAD
                    else -> COLOR_HTTP_OPTIONS
                },
                textColor = Color.White
            )
            Text(
                modifier = Modifier.weight(1f),
                text = response.request.name,
                style = MaterialTheme.typography.bodyLarge.bold()
            )

            HttpStatusCodeUI(
                response.responseCode
            )
        }

        RapidQAUrlUI(
            url = response.request.url,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        if (response.request.tags.isNotEmpty())
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                response.request.tags.forEach {
                    TagUI(
                        tag = it,
                        bgColor = if (it.contains("Delayed"))
                            Purple80
                        else if (it.contains("Mocked")) Pink80
                        else Color.LightGray,
                        textColor = if (it.contains("Delayed"))
                            Color.White
                        else if (it.contains("Mocked")) Color.White
                        else Color.Black
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }

        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(end = 8.dp),
            text = "Total time : " + response.totalTime.toString() + " ms, Server wait time : " + response.serverResponseTime + " ms",
            style = MaterialTheme.typography.labelMedium.bold().italic(),
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                color = TEXT_COLOR_LIGHT,
                text = "Message: " + if (response.responseMessage.isNotEmpty()) response.responseMessage else "N/A",
                style = MaterialTheme.typography.labelSmall.bold().italic(),
            )

            Text(
                color = TEXT_COLOR_LIGHT,
                text = response.request.time.asTime(),
                style = MaterialTheme.typography.labelSmall.bold().italic(),
                textAlign = androidx.compose.ui.text.style.TextAlign.End,
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        if (response.request.headers.isNotEmpty()) {
            Text(
                text = "Request Headers:",
                style = MaterialTheme.typography.labelMedium.bold(),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            RapidQAKeyValueListUI(
                keyValues = response.request.headers,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        response.request.body?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(top = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Request Body: ${response.request.contentType}",
                    style = MaterialTheme.typography.labelMedium.bold(),
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .weight(1f)
                        .padding(top = 8.dp)
                        .padding(bottom = 4.dp)
                )
                TagUI(
                    textColor = Color.Black,
                    tag = response.request.body.sizeInBytes().toHumanReadableSize()
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.LightGray)
                    .padding(1.dp)
            ) {
                if (response.request.contentType.contains("json")) {
                    RapidQAJsonViewerUI(
                        jsonString = response.request.body,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 500.dp)
                            .background(Color.White)
                            .padding(8.dp)
                    )
                } else {
                    Text(
                        text = response.request.body,
                        style = MaterialTheme.typography.labelSmall,
                        color = TEXT_COLOR_LIGHT,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 500.dp)
                            .background(Color.White)
                            .padding(horizontal = 7.dp, vertical = 4.dp)
                    )
                }
            }
        } ?: Text(
            text = "Request Body: N/A",
            style = MaterialTheme.typography.labelMedium.bold(),
            modifier = Modifier
                .padding(vertical = 4.dp)
                .padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.size(16.dp))

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
        } else {
            Text(
                text = "Response Headers: N/A",
                style = MaterialTheme.typography.labelMedium.bold(),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        if (response.responseBody.isNotEmpty()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(top = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Response Body: ${response.responseContentType}",
                    style = MaterialTheme.typography.labelMedium.bold(),
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .padding(top = 8.dp)
                        .weight(1f)
                        .padding(bottom = 4.dp)
                )

                TagUI(
                    textColor = Color.Black,
                    tag = response.responseBody.sizeInBytes().toHumanReadableSize()
                )
            }

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
                                    color = TEXT_COLOR_LIGHT,
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
                        color = TEXT_COLOR_LIGHT,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 500.dp)
                            .background(Color.White)
                            .padding(8.dp)
                            .verticalScroll(rememberScrollState())
                    )
                }
            }
        } else {
            Text(
                text = "Response Body: N/A",
                style = MaterialTheme.typography.labelMedium.bold(),
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .padding(top = 8.dp)
            )
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


@Preview(showBackground = true)
@Composable
private fun V2() {
    RapidQAResponseCardUI(
        response = RapidQAPreviewSamples.SAMPLE_RESPONSE
    )
}