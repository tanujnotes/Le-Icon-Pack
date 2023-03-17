package com.tanujnotes.leiconpack

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tanujnotes.leiconpack.ui.MainViewModel
import com.tanujnotes.leiconpack.ui.NavGraph
import com.tanujnotes.leiconpack.ui.theme.LeIconPackTheme
import com.tanujnotes.leiconpack.util.MenuItem
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel:MainViewModel = viewModel()
            LeIconPackTheme {
                 DrawerContent(navController, viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeIconPackApp(
    navController: NavHostController,
    viewModel: MainViewModel,
    showApplyDialog: MutableState<Boolean>,
    selectedItem: MutableState<MenuItem>,
    openDrawer: () -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current as Activity

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = when (currentRoute) {
                        MenuItem.WhyLeIcons.title.lowercase().trim() -> {
                            "${MenuItem.WhyLeIcons.title}?"
                        }
                        MenuItem.IconRequest.title.lowercase().trim() -> {
                            MenuItem.IconRequest.title
                        }
                        else -> MenuItem.Home.title
                    }
                    )
                        },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    when (currentRoute) {
                        MenuItem.WhyLeIcons.title.lowercase().trim() -> {
                            IconButton(onClick = {
                                selectedItem.value = MenuItem.WhyLeIcons
                                navController.popBackStack()
                            }) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                            }
                        }
                        MenuItem.IconRequest.title.lowercase().trim() -> {
                            IconButton(onClick = {
                                navController.popBackStack()
                            }) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                            }
                        }
                        else -> {
                            IconButton(onClick = openDrawer) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            when (currentRoute) {
                MenuItem.IconRequest.title.lowercase().trim() ->{
                    FloatingActionButton(onClick = {
                        val stringBuilder = StringBuilder()
                        stringBuilder.append("Manufacturer : ${Build.MANUFACTURER.uppercase()}\n" +
                                "Model: ${Build.MODEL.uppercase()}\n\n")
                        Log.d("ViewModel", "${viewModel.missingIconApps.toList()}")
                        viewModel.missingIconApps.size
                        viewModel.missingIconApps.filter { it.checked }.forEach { app->
                            stringBuilder.append("Name: ${app.appName}\n ComponentInfo: ${app.componentName}\n\n")
                        }
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("leiconpack@gmail.com"))
                            putExtra(Intent.EXTRA_SUBJECT,"Missing Icon Report")
                            putExtra(Intent.EXTRA_TEXT, stringBuilder.toString())
                        }
                        try {
                            context.startActivity(Intent.createChooser(emailIntent, "Choose email app"))
                            viewModel.resetSelection()
                        }catch (e:ActivityNotFoundException){
                            Toast.makeText(
                                context,
                                "No email activity found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Send, contentDescription ="action request" )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
           NavGraph(navController = navController, viewModel, showApplyDialog, selectedItem)
            /* Column(
                 modifier = Modifier.fillMaxSize()
             ) {
                 LazyRow(
                     modifier = Modifier.fillMaxWidth(),
                     horizontalArrangement = Arrangement.SpaceBetween,
                     userScrollEnabled = false
                 ) {
                     items(viewModel.lettersList) { letters ->
                         if (viewModel.rectangularIconsShape.value) {
                             CustomIcon(letters = letters, RoundedCornerShape(12.dp))
                         } else {
                             CustomIcon(letters = letters, cornerShape = CircleShape)
                         }
                     }
                 }

                 Divider(modifier = Modifier.padding(top = 60.dp, bottom = 60.dp))

                 Column(
                     modifier = Modifier.verticalScroll(rememberScrollState())
                 ) {

                     // THEME
                     Text(text = stringResource(R.string.icons_theme))
                     Row(
                         modifier = Modifier
                             .fillMaxWidth()
                             .selectableGroup()
                     ) {
                         viewModel.themeRadioOptions.forEach { theme ->
                             SelectableGroup(
                                 selectableOption = theme,
                                 selected = (theme == viewModel.themeSelectedOption.value),
                                 onClick = {
                                     viewModel.onThemeOptionSelected(theme)
                                 }
                             )
                             Spacer(modifier = Modifier.weight(1f))
                         }
                     }

                     Spacer(modifier = Modifier.height(60.dp))

                     // SHAPE
                     Text(text = stringResource(id = R.string.shape))
                     Row(
                         modifier = Modifier
                             .fillMaxWidth()
                             .selectableGroup()
                     ) {
                         viewModel.shapeRadioOptions.forEach { shape ->
                             SelectableGroup(
                                 selectableOption = shape,
                                 selected = (shape == viewModel.shapeSelectedOption.value),
                                 onClick = {
                                     viewModel.onShapeOptionSelected(shape)
                                     if (shape == "Circle") {
                                         viewModel.onCircleShapeIconsSelected()
                                     } else {
                                         viewModel.onRectangularShapeIconsSelected()
                                     }
                                 }
                             )
                             Spacer(modifier = Modifier.weight(1f))
                         }
                         Spacer(modifier = Modifier.weight(1f))
                     }

                     Spacer(modifier = Modifier.height(60.dp))

                     Button(
                         modifier = Modifier.fillMaxWidth(),
                         onClick = {
                             applyIcons(context)
                         }
                     ) {
                         Text(stringResource(R.string.apply_icons))
                     }

                 }
             }*/
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(navController:NavHostController, viewModel: MainViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current as Activity
    val items = viewModel.drawerItems
    val selectedItem = remember{ mutableStateOf(items[0]) }
    val showApplyDialog = remember { mutableStateOf(false)    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(drawerShape = RectangleShape) {
                LazyColumn(modifier = Modifier.fillMaxWidth(.8f)
                ) {
                    item {
                        Box(modifier = Modifier
                            .fillParentMaxWidth()
                            .height(150.dp)
                            .background(MaterialTheme.colorScheme.primary)) {
                            Text(
                                text = stringResource(id = R.string.app_name),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color=MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.BottomStart)
                            )
                        }
                    }
                    items(items = items){
                        TextButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            contentPadding = PaddingValues(start = 16.dp),
                            onClick = {
                            selectedItem.value = it
                            when(selectedItem.value){
                                is MenuItem.Home ->{
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                }
                                is MenuItem.Apply ->{
                                    applyIcons(context, showApplyDialog)
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                }
                                is MenuItem.WhyLeIcons ->{
                                    navController.navigate(route = MenuItem.WhyLeIcons.title.lowercase().trim())
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                }
                                else -> {Unit}
                            }


                        }
                        ) {
                            Icon(
                                painter = painterResource(id = it.icon),
                                contentDescription = null,
                                tint = if (it == MenuItem.Home) {
                                    MaterialTheme.colorScheme.surfaceTint
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                }
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (it.title == MenuItem.WhyLeIcons.title) "${it.title}?" else it.title,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Start,
                                color = if (it == MenuItem.Home) {
                                    MaterialTheme.colorScheme.surfaceTint
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            )
                        }
                    }
                }
            }

        }
    ){
        LeIconPackApp(
            viewModel = viewModel,
            navController = navController,
            showApplyDialog = showApplyDialog,
            selectedItem = selectedItem
        ){
            coroutineScope.launch {
                drawerState.open()
            }
        }
    }

}


@Suppress("DEPRECATION")
fun applyIcons(context: Context, showApplyDialog: MutableState<Boolean>,) {
    val defaultLauncherIntent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_HOME)
    }
    val defaultLauncherPkg = context.packageManager
        .resolveActivity(defaultLauncherIntent, PackageManager.MATCH_DEFAULT_ONLY)
        ?.activityInfo
        ?.packageName
    when (defaultLauncherPkg) {
        context.getString(R.string.nova_package_name) -> {
            val novaIntent = Intent("com.teslacoilsw.launcher.APPLY_ICON_THEME")
            novaIntent.apply {
                setPackage("com.teslacoilsw.launcher")
                putExtra(
                    "com.teslacoilsw.launcher.extra.ICON_THEME_PACKAGE",
                    context.packageName
                )
            }
            openIntent(context, novaIntent, showApplyDialog)
        }

        context.getString(R.string.niagara_package_name) -> {
            val niagaraIntent = Intent("bitpit.launcher.APPLY_ICONS").apply {
                setPackage("bitpit.launcher")
                putExtra("packageName", context.packageName)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            openIntent(context, niagaraIntent, showApplyDialog)
        }

        context.getString(R.string.apex_package_name) -> {
            val apexIntent = Intent("com.anddoes.launcher.SET_THEME").apply {
                putExtra("com.anddoes.launcher.THEME_PACKAGE_NAME", context.packageName)
            }
            openIntent(context, apexIntent, showApplyDialog)

        }
        context.getString(R.string.lawnchair_package_name) -> {
            val lawnchairIntent = Intent("ch.deletescape.lawnchair.APPLY_ICONS", null).apply {
                setPackage(defaultLauncherPkg)
                putExtra("packageName", context.packageName)
            }
            openIntent(context, lawnchairIntent, showApplyDialog)
        }

        context.getString(R.string.action_package_name) -> {
            val actionIntent =
                context.packageManager.getLaunchIntentForPackage(defaultLauncherPkg)?.apply {
                    putExtra("apply_icon_pack", context.packageName)
                }
            openIntent(context, actionIntent!!, showApplyDialog)
        }

        context.getString(R.string.smart_free_package_name),
        context.getString(R.string.smart_pro_package_name),
        context.getString(R.string.smart_special_package_name) -> {
            val smartIntent = Intent("ginlemon.smartlauncher.setGSLTHEME").apply {
                putExtra("package", context.packageName)
            }
            openIntent(context, smartIntent, showApplyDialog)

        }

        context.getString(R.string.go_package_name) -> {
            val goIntent = context.packageManager.getLaunchIntentForPackage(defaultLauncherPkg)
            val go = Intent("$defaultLauncherPkg.MyThemes.mythemeaction").apply {
                putExtra("type", 1)
                putExtra("pkgname", context.packageName)
            }
            context.sendBroadcast(go)
            openIntent(context, goIntent!!, showApplyDialog)
        }

        context.getString(R.string.hyperion_package_name) -> {
            val hyperionIntent = Intent("projekt.launcher.CHANGE_ICONS").apply {
                setPackage(defaultLauncherPkg)
                putExtra("className", "projekt.launcher.ProjektLauncher")
            }
            openIntent(context, hyperionIntent, showApplyDialog)
        }

        context.getString(R.string.pixel_package_name) -> {
            val pixelIntent = Intent("com.tushar.launcher3.APPLY_ICONS", null).apply {
                setPackage(defaultLauncherPkg)
                putExtra("packageName", context.packageName)
            }
            openIntent(context, pixelIntent, showApplyDialog)
        }

        context.getString(R.string.poco_package_name) -> {
            val pocoIntent = Intent("com.mi.android.globallauncher.SET_ICON_PACK").apply {
                    setPackage(defaultLauncherPkg)
                    putExtra("pkg_name", context.packageName)
                }
            openIntent(context, pocoIntent, showApplyDialog)
        }

        context.getString(R.string.total_package_name) -> {
            val totalIntent = Intent("com.ss.launcher2.APPLY_ICON_THEME").apply {
                putExtra("packageName", context.packageName)
            }
            openIntent(context, totalIntent, showApplyDialog)
        }

        context.getString(R.string.google_pixel_package_name) -> {
            val pixelIntent =
                Intent("com.google.android.apps.nexuslauncher.APPLY_ICON_PACK").apply {
                    putExtra(
                        "com.google.android.apps.nexuslauncher.extra.ICON_PACK",
                        context.packageName
                    )
                }
            openIntent(context, pixelIntent, showApplyDialog)
        }

        context.getString(R.string.lynx_package_name) -> {
            val lynxIntent = Intent("org.n277.lynxlauncher.SET_THEME").apply {
                setPackage(defaultLauncherPkg)
                putExtra("package", context.packageName)
            }
            openIntent(context, lynxIntent, showApplyDialog)
        }

        else -> {
           showApplyDialog.value = true
        }
    }
}

private fun openIntent(context: Context, intent: Intent, showApplyDialog: MutableState<Boolean>) {
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        showApplyDialog.value = true
    }
}

@Composable
private fun SelectableGroup(
    selectableOption: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.selectable(
            selected = selected,
            onClick = onClick,
            role = Role.RadioButton
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Text(text = selectableOption, modifier = Modifier.padding(start = 16.dp))
    }
}

@Composable
fun CustomIcon(letters: String, cornerShape: RoundedCornerShape, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(60.dp)
            .clip(cornerShape)
            .background(color = Color(0xFF212121)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = letters, color = Color.White, fontSize = 30.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    LeIconPackTheme(useDarkTheme = true) {
       // LeIconPackApp(viewModel = MainViewModel())
       DrawerContent(rememberNavController(),viewModel = MainViewModel())
    }
}
