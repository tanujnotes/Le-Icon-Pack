package com.tanujnotes.leiconpack

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.tanujnotes.leiconpack.ui.MainViewModel
import com.tanujnotes.leiconpack.ui.theme.LeIconPackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel = MainViewModel()

            LeIconPackTheme() {
                LeIconPackApp(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeIconPackApp(viewModel: MainViewModel) {

    val context = LocalContext.current
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            Column(
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
                    Text( text = stringResource(id = R.string.shape))
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
                            // Apply for Nova Launcher
                            val novaIntent = Intent("com.teslacoilsw.launcher.APPLY_ICON_THEME")
                            novaIntent.apply {
                                setPackage("com.teslacoilsw.launcher")
                                putExtra(
                                    "com.teslacoilsw.launcher.extra.ICON_THEME_PACKAGE",
                                    context.packageName
                                )
                            }
                            try {
                                context.startActivity(novaIntent)
                            }catch (e: ActivityNotFoundException){
                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            }


                            // Apply for Niagara Launcher-- not configured yet
                            /*val niagaraIntent = Intent("bitpit.launcher.APPLY_ICONS").apply {
                                setPackage("bitpit.launcher")
                                putExtra("package", packageName)
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            try {
                                startActivity(niagaraIntent)
                            }catch (e:ActivityNotFoundException){
                                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                            }*/

                        }
                    ) {
                        Text(stringResource(R.string.apply_icons))
                    }

                }
            }
        }
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
            .size(72.dp)
            .clip(cornerShape)
            .background(color = Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(text = letters, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LeIconPackTheme {
        LeIconPackApp(viewModel = MainViewModel())
    }
}
