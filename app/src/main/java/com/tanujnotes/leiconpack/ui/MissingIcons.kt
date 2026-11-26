package com.tanujnotes.leiconpack.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toDrawable
import coil.compose.rememberAsyncImagePainter
import com.tanujnotes.leiconpack.R
import com.tanujnotes.leiconpack.model.AppInfo
import com.tanujnotes.leiconpack.ui.theme.LeIconPackTheme
import com.tanujnotes.leiconpack.util.sendMissingIconReport
import com.tanujnotes.leiconpack.util.showToast

@Composable
fun MissingIconsScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    // Scan once per visit to this screen (not again on rotation), so apps installed since the
    // last visit show up and the selection starts fresh.
    var hasScanned by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!hasScanned) {
            viewModel.loadMissingIconApps()
            hasScanned = true
        }
    }

    val context = LocalContext.current
    MissingIconsContent(
        isLoading = viewModel.isLoading,
        apps = viewModel.missingIconApps,
        onBack = onBack,
        onCheckedChange = viewModel::setChecked,
        onAllCheckedChange = viewModel::setAllChecked,
        onReportClick = {
            // The ViewModel hears when an email app is picked from the chooser.
            if (!context.sendMissingIconReport(viewModel.buildMissingIconReport())) {
                context.showToast(R.string.no_email_app)
            }
        }
    )
}

@Composable
private fun MissingIconsContent(
    isLoading: Boolean,
    apps: List<AppInfo>,
    onBack: () -> Unit,
    onCheckedChange: (componentName: String, checked: Boolean) -> Unit,
    onAllCheckedChange: (Boolean) -> Unit,
    onReportClick: () -> Unit,
) {
    val hasApps = !isLoading && apps.isNotEmpty()
    val selectedCount = apps.count { it.checked }

    LettersScreen(
        topBar = { BackTopBar(onBack = onBack) },
        bottomBar = {
            if (hasApps) {
                BottomBar {
                    PrimaryButton(
                        text = stringResource(R.string.report_selected),
                        onClick = onReportClick,
                        enabled = selectedCount > 0
                    )
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                ScreenHeader(
                    title = stringResource(R.string.missing_icons_title),
                    description = if (isLoading || hasApps) {
                        stringResource(R.string.missing_icons_description)
                    } else {
                        null
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            when {
                isLoading -> item {
                    Box(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(top = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                apps.isEmpty() -> item {
                    Text(
                        text = stringResource(R.string.no_missing_icons),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = ScreenPadding)
                    )
                }

                else -> {
                    item {
                        SelectAllRow(
                            selectedCount = selectedCount,
                            total = apps.size,
                            onCheckedChange = onAllCheckedChange
                        )
                    }
                    items(items = apps, key = { it.componentName }) { app ->
                        InsetDivider()
                        AppRow(app = app, onCheckedChange = { onCheckedChange(app.componentName, it) })
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectAllRow(selectedCount: Int, total: Int, onCheckedChange: (Boolean) -> Unit) {
    val allChecked = selectedCount == total
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(value = allChecked, role = Role.Checkbox, onValueChange = onCheckedChange)
            .heightIn(min = 52.dp)
            .padding(horizontal = ScreenPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.missing_icons_selected, selectedCount, total),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = stringResource(R.string.select_all),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        // The whole row is toggleable, so the checkbox itself doesn't handle clicks.
        Checkbox(checked = allChecked, onCheckedChange = null)
    }
}

@Composable
private fun AppRow(app: AppInfo, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(value = app.checked, role = Role.Checkbox, onValueChange = onCheckedChange)
            .heightIn(min = 60.dp)
            .padding(horizontal = ScreenPadding, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = app.appIcon),
            // The app name is read out from the text next to it.
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = app.appName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        Checkbox(checked = app.checked, onCheckedChange = null)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MissingIconsPreview() {
    val icon = android.graphics.Color.DKGRAY.toDrawable()
    LeIconPackTheme {
        Surface {
            MissingIconsContent(
                isLoading = false,
                apps = listOf(
                    AppInfo(icon, "Calculator", "com.example/.Calculator"),
                    AppInfo(icon, "Notes", "com.example/.Notes", checked = false),
                ),
                onBack = {},
                onCheckedChange = { _, _ -> },
                onAllCheckedChange = {},
                onReportClick = {}
            )
        }
    }
}
