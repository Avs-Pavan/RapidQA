package com.pavan.rapidqa.interceptors.delay

import com.pavan.rapidqa.tag.RapidQATag


/**
 * Description: Tag added to the request object to identify it as a delayed request.
 *
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 2021-08-29
 * @githubUrl https://github.com/Avs-Pavan
 *
 */


data class RapidQATagDelay(
    val timeMills: Long
) : RapidQATag