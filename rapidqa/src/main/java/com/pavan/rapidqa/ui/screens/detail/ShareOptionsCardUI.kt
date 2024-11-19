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

@Composable
fun ShareOptionsCardUI(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true
) {
    var fileName by remember { mutableStateOf("") }
    val selectedType = remember { mutableStateOf("OpenAPI") }
    val options = listOf("OpenAPI", "Text", "Json")

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
                    Text("File name", color = Color.White)
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
                        label = { Text(option) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color.White,
                            selectedLabelColor = Teal40,
                            labelColor = Color.White,
                            selectedLeadingIconColor = Teal40,
                            iconColor = Color.LightGray
                        ),
                        leadingIcon = if (option == "OpenAPI") {
                            {
                                Icon(
                                    painterResource(id = R.drawable.baseline_code_24),
                                    contentDescription = null,
                                )
                            }
                        } else if (option == "Text") {
                            {
                                Icon(
                                    painterResource(id = R.drawable.baseline_text_snippet_24),
                                    contentDescription = null,
                                )
                            }
                        } else {
                            {
                                Icon(
                                    painterResource(id = R.drawable.braces),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    )
                }
            }

            Button(
                onClick = { /* Handle share action */ },
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

@Composable
@Preview
private fun ShareOptionsCardUIPreview() {
    ShareOptionsCardUI()
}
