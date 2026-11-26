package com.tanujnotes.leiconpack.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    onApplyClick: () -> Unit,
) {
    // navigateUp does nothing on Home, so a quick second tap on Back can't leave an empty screen.
    val goBack: () -> Unit = { navController.navigateUp() }
    NavHost(navController = navController, startDestination = Destination.Home.route) {
        composable(route = Destination.Home.route) {
            HomeScreen(
                onApplyClick = onApplyClick,
                onWhyLettersClick = { navController.open(Destination.WhyLetters) },
                onReportMissingClick = { navController.open(Destination.MissingIcons) }
            )
        }
        composable(route = Destination.Apply.route) {
            ApplyScreen(onBack = goBack)
        }
        composable(route = Destination.WhyLetters.route) {
            WhyLettersScreen(onBack = goBack)
        }
        composable(route = Destination.MissingIcons.route) {
            MissingIconsScreen(viewModel = viewModel, onBack = goBack)
        }
    }
}

/** Opens [destination], unless it's already showing (a double tap would otherwise open it twice). */
fun NavHostController.open(destination: Destination) {
    navigate(destination.route) { launchSingleTop = true }
}
