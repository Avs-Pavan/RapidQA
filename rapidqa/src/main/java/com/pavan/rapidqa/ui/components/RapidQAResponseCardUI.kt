/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/13/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pavan.rapidqa.ui.RapidQAPreviewSamples
import okhttp3.Response


@Composable
fun RapidQAResponseCardMinUI(
    response: Response,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        HttpStatusCodeUI(
            response.code(), modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        )
        RapidQARequestCardMinUI(
            request = response.request().toUIModel(),
            modifier = Modifier
        )
    }
}


@Composable
fun RapidQAResponseCardUI(
    response: Response,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        HttpStatusCodeUI(
            response.code(), modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        )
        RapidQARequestCardUI(
            request = response.request().toUIModel(),
            modifier = Modifier
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun V1() {
    RapidQAResponseCardMinUI(
        response = RapidQAPreviewSamples.SAMPLE_RESPONSE
    )
}
