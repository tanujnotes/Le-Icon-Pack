package com.tanujnotes.leiconpack.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tanujnotes.leiconpack.util.MenuItem

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    showApplyDialog: MutableState<Boolean>,
    selectedItem: MutableState<MenuItem>
) {
    NavHost(
        navController = navController,
        startDestination = MenuItem.Home.title.lowercase().trim()){

        composable(route = MenuItem.Home.title.lowercase().trim()){
            Dashboard(navController = navController, viewModel = viewModel, selectedItem, showApplyDialog = showApplyDialog)
        }
        composable(route = MenuItem.WhyLeIcons.title.lowercase().trim()){
            WhyLeIconPack()
        }
        composable(route = MenuItem.IconRequest.title.lowercase().trim()){
            MissingIcons(viewModel = viewModel)
        }
    }

}