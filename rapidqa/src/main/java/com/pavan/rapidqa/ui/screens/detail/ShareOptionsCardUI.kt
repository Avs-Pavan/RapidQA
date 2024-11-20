/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/18/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.screens.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pavan.rapidqa.R
import com.pavan.rapidqa.ui.theme.Teal40

sealed class RapidQaExportEvent {
    data class Share(
        val fileName: String,
        val description: String,
        val rapidQaExportOption: RapidQaExportOption
    ) :
        RapidQaExportEvent()
}

@Composable
fun ShareOptionsCardUI(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    onShareClick: (RapidQaExportEvent) -> Unit = { }
) {
    var fileName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val selectedType = remember { mutableStateOf(RapidQaExportOption.OPEN_API) }
    val options = RapidQaExportOption.entries.toTypedArray()

    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Teal40)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = fileName,
                onValueChange = {
                    fileName = it
                },
                colors = TextFieldDefaults.colors().copy(
                    focusedTextColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    focusedPrefixColor = Color.White,
                    focusedContainerColor = Teal40,
                    unfocusedContainerColor = Teal40
                ),
                label = {
                    Text("Name (Optional)", color = Color.White)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                maxLines = 1
            )

            Spacer(modifier = Modifier.size(4.dp))

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                },
                colors = TextFieldDefaults.colors().copy(
                    focusedTextColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    focusedPrefixColor = Color.White,
                    focusedContainerColor = Teal40,
                    unfocusedContainerColor = Teal40
                ),
                label = {
                    Text("Description (Optional)", color = Color.White)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(2.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
            )

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                options.forEach { option ->
                    FilterChip(
                        modifier = Modifier
                            .height(48.dp)
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        selected = selectedType.value == option,
                        onClick = { selectedType.value = option },
                        label = { Text(option.displayName) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color.White,
                            selectedLabelColor = Teal40,
                            labelColor = Color.White,
                            selectedLeadingIconColor = Teal40,
                            iconColor = Color.White
                        ),
                        leadingIcon = when (option) {
//                            RapidQaExportOption.OPEN_API -> {
//                                {
//                                    Icon(
//                                        painterResource(id = R.drawable.baseline_code_24),
//                                        contentDescription = null,
//                                    )
//                                }
//                            }

                            RapidQaExportOption.TEXT -> {
                                {
                                    Icon(
                                        painterResource(id = R.drawable.baseline_text_snippet_24),
                                        contentDescription = null,
                                    )
                                }
                            }

                            RapidQaExportOption.OPEN_API -> {
                                {
                                    Icon(
                                        painterResource(id = R.drawable.braces),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    )
                }
            }

            Button(
                onClick = {
                    onShareClick(
                        RapidQaExportEvent.Share(
                            fileName = fileName,
                            description = description,
                            rapidQaExportOption = selectedType.value
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Share", color = Teal40)
            }
        }
    }
}


enum class RapidQaExportOption(val displayName: String) {
    OPEN_API("OpenAPI"),
    TEXT("Text"),
//    JSON("Json")
}

@Composable
@Preview
private fun ShareOptionsCardUIPreview() {
    ShareOptionsCardUI()
}
