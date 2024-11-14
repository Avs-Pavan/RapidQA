/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/13/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
                        Text(
                            text = response.responseBody,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp)
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


@Preview(showBackground = true)
@Composable
private fun V1() {
    RapidQAResponseCardMinUI(
        response = RapidQAPreviewSamples.SAMPLE_RESPONSE
    )
}
