/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/14/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.components

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pavan.rapidqa.ui.theme.bold
import com.sebastianneubauer.jsontree.JsonTree


@Composable
fun RapidQAJsonViewerUI(
    jsonString: String,
    modifier: Modifier = Modifier
) {
    JsonTree(
        textStyle = MaterialTheme.typography.labelSmall.bold(),
        json = jsonString,
        modifier = modifier,
        showItemCount = false,
        onLoading = {
            Text("Loading...")
        },
        onError = {
            Log.e("RapidQAJsonViewerUI", "Error: $it")
        }
    )

}