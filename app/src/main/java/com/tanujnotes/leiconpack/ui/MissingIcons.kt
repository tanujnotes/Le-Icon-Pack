package com.tanujnotes.leiconpack.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.tanujnotes.leiconpack.DisposableEffectWithLifecycle
import com.tanujnotes.leiconpack.R
import com.tanujnotes.leiconpack.model.AppInfo
import com.tanujnotes.leiconpack.ui.theme.LeIconPackTheme

@Composable
fun MissingIcons(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavController
) {
    val context = LocalContext.current as Activity
    val missingIconApps = viewModel.missingIconApps

    val dataStore = viewModel.getPrefDatastore(context)
    val isReviewShown = dataStore.isReviewed.collectAsState(initial = false).value

    LaunchedEffect(Unit) {
        if (missingIconApps.isEmpty()) {
            viewModel.getMissingApps(context)
        }
    }
    BackHandler {
        viewModel.selectAll(appList = missingIconApps, selected = true)
        navController.popBackStack()
    }
    DisposableEffectWithLifecycle(
        onResume = {
            if (!isReviewShown) {
                if (viewModel.showRateDialogOnResume.value)
                    viewModel.showReviewDialog.value = true
            }
        },
        onPause = {
            viewModel.showRateDialogOnResume.value = true
        }
    )

    val isLoading by viewModel.isLoading.collectAsState()
    val listState = rememberLazyListState()
    if (isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxHeight(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        state = listState
    ) {
        item {
            Spacer(modifier = modifier.height(20.dp))
        }
        itemsIndexed(items = missingIconApps) { index, appInfo ->
            AppRow(appList = missingIconApps, app = appInfo, index = index, viewModel = viewModel)
        }
        item {
            Spacer(modifier = modifier.height(60.dp))
        }

    }

}

@Composable
fun AppRow(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    app: AppInfo,
    appList: MutableList<AppInfo>,
    index: Int
) {
    val painter = rememberAsyncImagePainter(model = app.appIcon)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = "icon",
                modifier = modifier.size(60.dp)
            )
            Text(
                text = app.appName,
                fontFamily = FontFamily(Font(R.font.inter)),
            )
        }
        Checkbox(
            checked = app.checked,
            onCheckedChange = {
                viewModel.updateSelection(appList, index, it)
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RequestPreview() {
    LeIconPackTheme(useDarkTheme = true) {
        MissingIcons(viewModel = MainViewModel(), navController = rememberNavController())
    }
}