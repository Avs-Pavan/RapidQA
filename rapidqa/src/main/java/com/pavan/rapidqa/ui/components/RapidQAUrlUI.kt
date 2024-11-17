/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/13/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pavan.rapidqa.tracer.RapidQaURL
import com.pavan.rapidqa.ui.theme.URLColor
import com.pavan.rapidqa.ui.theme.bold
import com.pavan.rapidqa.ui.theme.italic


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RapidQAUrlUI(
    url: RapidQaURL,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = url.encodedUrl,
        style = MaterialTheme.typography.bodySmall.bold().italic().copy(color = URLColor),
    )
}


@Composable
@Preview(showBackground = true)
fun RapidQAUrlUIPreview() {
    RapidQAUrlUI(
        url = RapidQaURL(
            scheme = "https",
            host = "www.google.com",
            port = 443,
            path = "/search",
            queryParams = listOf("q" to "compose"),
            encodedUrl = "https://www.google.com/search?q=compose"
        )
    )
}