package com.pavan.rapidqa.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pavan.rapidqa.ui.RapidQAPreviewSamples.SAMPLE_GET_REQUEST
import okhttp3.Request

@Composable
fun RapidQARequestCardUI(
    request: Request,
    modifier: Modifier = Modifier,
) {

}

@Composable
@Preview
private fun V1() {
    RapidQARequestCardUI(
        request = SAMPLE_GET_REQUEST
    )
}

