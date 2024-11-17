/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/13/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pavan.rapidqa.ui.theme.bold


@Composable
fun TagUI(
    tag: String,
    bgColor: Color = Color.LightGray,
    textColor: Color = Color.White,
    paddingValues: PaddingValues = PaddingValues(vertical = 4.dp, horizontal = 8.dp),
    cornerRadius: Dp = 4.dp,
    modifier: Modifier = Modifier
) {
    Text(
        text = tag,
        style = MaterialTheme.typography.bodySmall.bold(),
        color = textColor,
        textAlign = TextAlign.Center,
        modifier = modifier
            .background(bgColor, RoundedCornerShape(cornerRadius))
            .padding(paddingValues)
    )
}


@Preview
@Composable
fun TagUIPreview() {
    TagUI("Tag", Color.Red)
}
