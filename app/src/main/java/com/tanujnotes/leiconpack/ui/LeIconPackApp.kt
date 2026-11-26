package com.tanujnotes.leiconpack.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tanujnotes.leiconpack.util.applyIconPack
import com.tanujnotes.leiconpack.util.openPlayStore

@Composable
fun LeIconPackApp(viewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Ask for a review when the user comes back from sending a missing icon report.
    LifecycleResumeEffect(viewModel) {
        viewModel.onResume()
        onPauseOrDispose { }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        NavGraph(
            navController = navController,
            viewModel = viewModel,
            onApplyClick = {
                // Launchers that can't be asked directly get the manual steps instead.
                if (!context.applyIconPack()) navController.open(Destination.Apply)
            }
        )
    }

    if (viewModel.showReviewDialog) {
        ReviewDialog(
            onRate = {
                context.openPlayStore()
                viewModel.onRateClicked()
            },
            onDismiss = viewModel::dismissReviewDialog
        )
    }
}
