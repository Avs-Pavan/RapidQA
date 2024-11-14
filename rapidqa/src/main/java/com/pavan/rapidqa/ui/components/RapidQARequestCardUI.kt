package com.pavan.rapidqa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pavan.rapidqa.ui.RapidQAPreviewSamples.SAMPLE_RAPID_QA_UI_MODEL
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_DELETE
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_GET
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_HEAD
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_OPTIONS
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_PATCH
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_POST
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_PUT
import com.pavan.rapidqa.ui.theme.Pink80
import com.pavan.rapidqa.ui.theme.Purple80
import com.pavan.rapidqa.ui.theme.bold
import com.pavan.rapidqa.ui.theme.italic
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.log10
import kotlin.math.pow


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RapidQARequestCardMinUI(
    request: RapidQARequestUIModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            TagUI(
                tag = request.method,
                bgColor = when (request.method.uppercase()) {
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
                text = request.name,
                style = MaterialTheme.typography.bodyLarge.bold()
            )

        }

        RapidQAUrlUI(
            url = request.url,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            request.tags.forEach {
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
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = request.time.asTime(),
            style = MaterialTheme.typography.labelSmall.bold().italic(),
            textAlign = androidx.compose.ui.text.style.TextAlign.End,
        )
    }
}

fun Long.asTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
    return format.format(date)
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RapidQARequestCardUI(
    request: RapidQARequestUIModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            TagUI(
                tag = request.method,
                bgColor = when (request.method.uppercase()) {
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
                text = request.name,
                style = MaterialTheme.typography.bodyLarge.bold()
            )

        }

        RapidQAUrlUI(
            url = request.url,
            modifier = Modifier.padding(vertical = 4.dp)
        )


        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            request.tags.forEach {
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
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = request.time.asTime(),
            style = MaterialTheme.typography.labelSmall.bold().italic(),
            textAlign = androidx.compose.ui.text.style.TextAlign.End,
        )

        if (request.headers.isNotEmpty()) {
            Text(
                text = "Request Headers:",
                style = MaterialTheme.typography.labelMedium.bold(),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            RapidQAKeyValueListUI(
                keyValues = request.headers,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        request.body?.let {
            Text(
                text = "Request Body: ${request.body.sizeInBytes().toHumanReadableSize()}",
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
                    text = request.body,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White)
                        .padding(horizontal = 7.dp, vertical = 4.dp)
                )
            }
        }
    }
}


fun String.sizeInBytes(): Int {
    return this.toByteArray().size
}

fun Int.toHumanReadableSize(): String {
    if (this <= 0) return "0 Bytes"
    val units = arrayOf("Bytes", "KB", "MB")
    val digitGroups =
        (log10(this.toDouble()) / log10(1024.0)).toInt().coerceAtMost(units.size - 1)
    return String.format(
        locale = Locale.getDefault(),
        "%.1f %s",
        this / 1024.0.pow(digitGroups.toDouble()),
        units[digitGroups],
    )
}

@Composable
@Preview(showBackground = true)
private fun V1() {
    RapidQARequestCardMinUI(
        request = SAMPLE_RAPID_QA_UI_MODEL
    )
}

@Composable
@Preview(showBackground = true)
private fun V2() {
    RapidQARequestCardUI(
        request = SAMPLE_RAPID_QA_UI_MODEL
    )
}

