package com.tanujnotes.leiconpack.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toDrawable
import coil.compose.rememberAsyncImagePainter
import com.tanujnotes.leiconpack.model.AppInfo
import com.tanujnotes.leiconpack.ui.theme.LeIconPackTheme

@Composable
fun MissingIcons(viewModel: MainViewModel) {
    // Scan once per visit to this screen (not again on rotation), so apps installed since the
    // last visit show up and the selection starts fresh.
    var hasScanned by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!hasScanned) {
            viewModel.loadMissingIconApps()
            hasScanned = true
        }
    }

    MissingIconsContent(
        isLoading = viewModel.isLoading,
        apps = viewModel.missingIconApps,
        onCheckedChange = viewModel::setChecked
    )
}

@Composable
private fun MissingIconsContent(
    isLoading: Boolean,
    apps: List<AppInfo>,
    onCheckedChange: (componentName: String, checked: Boolean) -> Unit,
) {
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        // Leave room at the bottom so the last row isn't hidden behind the send button.
        contentPadding = PaddingValues(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 80.dp)
    ) {
        items(items = apps, key = { it.componentName }) { app ->
            AppRow(app = app, onCheckedChange = { onCheckedChange(app.componentName, it) })
        }
    }
}

@Composable
private fun AppRow(app: AppInfo, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(value = app.checked, role = Role.Checkbox, onValueChange = onCheckedChange)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = app.appIcon),
                // The app name is read out from the text next to it.
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
            Text(text = app.appName)
        }
        // The whole row is toggleable, so the checkbox itself doesn't handle clicks.
        Checkbox(checked = app.checked, onCheckedChange = null)
    }
}

@Preview(showBackground = true)
@Composable
private fun MissingIconsPreview() {
    val icon = android.graphics.Color.DKGRAY.toDrawable()
    LeIconPackTheme(useDarkTheme = true) {
        Surface {
            MissingIconsContent(
                isLoading = false,
                apps = listOf(
                    AppInfo(icon, "Calculator", "com.example/.Calculator"),
                    AppInfo(icon, "Notes", "com.example/.Notes", checked = false),
                ),
                onCheckedChange = { _, _ -> }
            )
        }
    }
}
