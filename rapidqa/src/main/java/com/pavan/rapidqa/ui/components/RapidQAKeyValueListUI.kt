/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/13/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RapidQAKeyValueListUI(
    modifier: Modifier = Modifier,
    keyValues: List<Pair<String, String>>
) {
    Column(
        modifier = modifier
    ) {
        keyValues.forEach {
            HorizontalDivider()
            RapidQAKeyValueUI(model = it)
            if (it == keyValues.last())
                HorizontalDivider()
        }
    }
}


@Composable
fun RapidQAKeyValueUI(
    model: Pair<String, String>,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        VerticalDivider()
        Text(
            text = model.first,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
                .padding(vertical = 4.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.labelSmall
        )

        VerticalDivider()

        Text(
            text = model.second,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.labelSmall
        )
        VerticalDivider()

    }
}


@Composable
@Preview(showBackground = true)
private fun V1() {
    RapidQAKeyValueUI(
        model = Pair(
            "Key----------------",
            "Value-------------\nValue-------------\nValue-------------\nValue-------------"
        )
    )
}

@Composable
@Preview(showBackground = true)
private fun V2() {
    RapidQAKeyValueListUI(
        keyValues = listOf(
            Pair(
                "Key----------------",
                "Value-------------\nValue-------------\nValue-------------\nValue-------------"
            ),
            Pair(
                "Key----------------",
                "Value-------------\nValue-------------\nValue-------------\nValue-------------"
            )
        )
    )
}