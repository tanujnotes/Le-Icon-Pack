package com.tanujnotes.leiconpack.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tanujnotes.leiconpack.R
import com.tanujnotes.leiconpack.util.applyIconPack
import com.tanujnotes.leiconpack.util.openPlayStore
import com.tanujnotes.leiconpack.util.sendMissingIconReport
import com.tanujnotes.leiconpack.util.showToast
import kotlinx.coroutines.launch

@Composable
fun LeIconPackApp(viewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var showApplyDialog by rememberSaveable { mutableStateOf(false) }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val destination = Destination.fromRoute(backStackEntry?.destination?.route)

    val applyIcons = {
        if (!context.applyIconPack()) showApplyDialog = true
    }

    // Ask for a review when the user comes back from sending a missing icon report.
    LifecycleResumeEffect(viewModel) {
        viewModel.onResume()
        onPauseOrDispose { }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            AppDrawer(
                currentDestination = destination,
                onItemClick = { item ->
                    coroutineScope.launch { drawerState.close() }
                    when (item) {
                        DrawerItem.Home -> Unit
                        DrawerItem.Apply -> applyIcons()
                        DrawerItem.WhyLetters -> navController.navigate(Destination.WhyLetters.route)
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    destination = destination,
                    onMenuClick = { coroutineScope.launch { drawerState.open() } },
                    onBackClick = { navController.popBackStack() }
                )
            },
            floatingActionButton = {
                if (destination == Destination.MissingIcons) {
                    FloatingActionButton(onClick = {
                        when {
                            !viewModel.hasSelection -> context.showToast(R.string.select_apps_to_report)
                            context.sendMissingIconReport(viewModel.buildMissingIconReport()) ->
                                viewModel.onReportSent()

                            else -> context.showToast(R.string.no_email_app)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = stringResource(R.string.cd_send_report)
                        )
                    }
                }
            }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                NavGraph(
                    navController = navController,
                    viewModel = viewModel,
                    onApplyIcons = applyIcons
                )
            }
        }
    }

    if (showApplyDialog) {
        ApplyInstructionsDialog(onDismiss = { showApplyDialog = false })
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(
    destination: Destination,
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = stringResource(destination.title)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            if (destination == Destination.Home) {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(R.string.cd_open_menu)
                    )
                }
            } else {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.cd_navigate_up)
                    )
                }
            }
        }
    )
}

@Composable
private fun AppDrawer(
    currentDestination: Destination,
    onItemClick: (DrawerItem) -> Unit,
) {
    ModalDrawerSheet(
        drawerShape = RectangleShape,
        windowInsets = WindowInsets(0)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(.8f),
            contentPadding = WindowInsets.navigationBars.asPaddingValues()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                        .statusBarsPadding()
                        .height(150.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomStart)
                    )
                }
            }
            items(items = DrawerItem.entries) { item ->
                val isCurrent = when (item) {
                    DrawerItem.Home -> currentDestination == Destination.Home
                    DrawerItem.WhyLetters -> currentDestination == Destination.WhyLetters
                    DrawerItem.Apply -> false
                }
                val color = if (isCurrent) {
                    MaterialTheme.colorScheme.surfaceTint
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    contentPadding = PaddingValues(start = 16.dp),
                    onClick = { onItemClick(item) }
                ) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        tint = color
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(item.title),
                        fontSize = 16.sp,
                        color = color,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                }
            }
        }
    }
}
