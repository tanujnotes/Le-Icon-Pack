package com.tanujnotes.leiconpack.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.tanujnotes.leiconpack.R
import com.tanujnotes.leiconpack.model.AppInfo
import com.tanujnotes.leiconpack.ui.theme.LeIconPackTheme

@Composable
fun MissingIcons(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val context = LocalContext.current as Activity
    val missingIconApps = viewModel.missingIconApps
    LaunchedEffect(Unit){
        if (missingIconApps.isEmpty()){
            viewModel.getMissingApps(context)
        }
    }
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
            AppRow(appList = missingIconApps,app = appInfo, index = index, viewModel = viewModel)
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
    index:Int
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
                viewModel.updateSelection(appList,index, it)
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RequestPreview() {
    LeIconPackTheme(useDarkTheme = true) {
        MissingIcons(viewModel = MainViewModel())
    }
}