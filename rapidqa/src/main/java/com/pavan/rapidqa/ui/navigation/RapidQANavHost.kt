/**
 * @author Venkata Sai Pavan, Arepalli <avspavan1234@gmail.com>
 * @createdAt 11/12/24
 * @githubUrl https://github.com/Avs-Pavan
 *
 */

package com.pavan.rapidqa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pavan.rapidqa.store.RapidQADataStore
import com.pavan.rapidqa.tracer.RapidQATraceRecord
import com.pavan.rapidqa.ui.screens.detail.TraceDetailScreenUI
import com.pavan.rapidqa.ui.screens.list.TraceListScreenUI
import com.pavan.rapidqa.ui.screens.list.TraceListViewModel

@Composable
fun RapidQANavHost(
    dataStore: RapidQADataStore<Long, RapidQATraceRecord>,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val viewModel = TraceListViewModel(dataStore = dataStore)

    NavHost(navController = navController, startDestination = RapidQANavigation.Routes.TRACE_LIST) {

        composable(RapidQANavigation.Routes.TRACE_LIST) {
            TraceListScreenUI(viewModel = viewModel, modifier = modifier)
        }
        composable(RapidQANavigation.Routes.TRACE_DETAILS) {
            TraceDetailScreenUI(modifier = modifier)
        }
    }
}