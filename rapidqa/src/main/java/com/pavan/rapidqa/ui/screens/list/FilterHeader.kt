/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/18/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pavan.rapidqa.ui.theme.Teal40


@Composable
fun FilterHeader(
    selectedResponseCode: ResponseCode,
    onResponseCodeSelected: (ResponseCode) -> Unit,
    selectedMethodType: MethodType,
    onMethodTypeSelected: (MethodType) -> Unit,
    showFilters: Boolean,
    modifier: Modifier = Modifier
) {
    if (showFilters) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            val filterColors = FilterChipDefaults.filterChipColors().copy(
                selectedLabelColor = Color.White,
                selectedContainerColor = Teal40
            )

            Text(text = "Request Methods", modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MethodType.entries.forEach { method ->
                    FilterChip(
                        colors = filterColors,
                        selected = selectedMethodType == method,
                        onClick = { onMethodTypeSelected(method) },
                        label = { Text(method.method) },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Text(text = "Response Codes", modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ResponseCode.entries.forEach { code ->
                    FilterChip(
                        colors = filterColors,
                        selected = selectedResponseCode == code,
                        onClick = { onResponseCodeSelected(code) },
                        label = { Text(code.code) },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun FilterHeaderPreview() {
    FilterHeader(
        selectedResponseCode = ResponseCode.ALL,
        onResponseCodeSelected = {},
        selectedMethodType = MethodType.ALL,
        onMethodTypeSelected = {},
        showFilters = true
    )
}

enum class ResponseCode(val code: String) {
    ALL("All"),
    CODE_200("200+"),
    CODE_300("300+"),
    CODE_400("400+"),
    CODE_500("500+")
}

enum class MethodType(val method: String) {
    ALL("All"),
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE")
}

