/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/9/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.mocker

import com.pavan.rapidqa.tag.RapidQATag

data class RapidQATagMocked(
    val fileName: String,
    val responseCode: Int
) : RapidQATag