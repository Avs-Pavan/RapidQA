/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/18/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.exporter

import android.content.Context
import com.pavan.rapidqa.tracer.RapidQATraceRecord
import java.io.File

sealed interface RapidQaExporter {
    fun export(context: Context, traceRecord: RapidQATraceRecord, fileName: String): File
}