/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/12/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.screens.list

import com.pavan.rapidqa.tracer.RapidQATraceRecord

data class TraceListScreenUIState (
    val traces: List<RapidQATraceRecord> = emptyList(),
    val selectedResponseCode: ResponseCode = ResponseCode.ALL,
    val selectedMethodType: MethodType = MethodType.ALL
)