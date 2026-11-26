package com.tanujnotes.leiconpack.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    onApplyIcons: () -> Unit,
) {
    NavHost(navController = navController, startDestination = Destination.Home.route) {
        composable(route = Destination.Home.route) {
            Dashboard(
                onApplyIcons = onApplyIcons,
                onWhyLettersClick = { navController.navigate(Destination.WhyLetters.route) },
                onReportMissingClick = { navController.navigate(Destination.MissingIcons.route) }
            )
        }
        composable(route = Destination.WhyLetters.route) {
            WhyLeIconPack()
        }
        composable(route = Destination.MissingIcons.route) {
            MissingIcons(viewModel = viewModel)
        }
    }
}
