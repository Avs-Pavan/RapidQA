package com.pavan.rapidqa.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import okhttp3.Request


@Composable
fun RapidQARequestCardUI(
    request: Request,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
//        Text(
//            text = request.name,
//            style = MaterialTheme.typography.bodyMedium
//        )
//        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = request.method(),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = request.url().toString(),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = request.body().toString(),
            style = MaterialTheme.typography.bodyMedium
        )

    }

}

//@Composable
//@Preview
//private fun V1() {
//    RapidQARequestCardUI(
//        request = SAMPLE_RAPID_QA_UI_MODEL
//    )
//}

