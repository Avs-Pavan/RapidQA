/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/11/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.components

import androidx.compose.runtime.Immutable
import okhttp3.HttpUrl

@Immutable
data class RapidQARequestUIModel(
    val name: String,
    val method: String,
    val url: HttpUrl,
    val headers: List<Pair<String, String>>,
    val body: String,
    val tags: List<String>,
)
