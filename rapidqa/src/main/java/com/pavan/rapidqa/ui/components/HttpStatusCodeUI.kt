/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/13/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_100
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_200
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_300
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_400
import com.pavan.rapidqa.ui.theme.COLOR_HTTP_500

@Composable
fun HttpStatusCodeUI(
    statusCode: Int,
    modifier: Modifier = Modifier
) {
    TagUI(
        modifier = modifier,
        tag = statusCode.toString(),
        bgColor = when (statusCode) {
            in 200..299 -> COLOR_HTTP_200
            in 300..399 -> COLOR_HTTP_300
            in 400..499 -> COLOR_HTTP_400
            in 500..599 -> COLOR_HTTP_500
            else -> COLOR_HTTP_100
        }
    )
}

@Composable
@Preview
fun HttpStatusCodeUIPreview() {
    HttpStatusCodeUI(200)

}