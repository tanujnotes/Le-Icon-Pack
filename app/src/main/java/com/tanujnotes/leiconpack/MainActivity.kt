package com.tanujnotes.leiconpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tanujnotes.leiconpack.ui.MainViewModel
import com.tanujnotes.leiconpack.ui.theme.LeIconPackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeIconPackTheme {
                LeIconPackApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeIconPackApp() {

    val viewModel = MainViewModel()

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
                        CustomIcon(letters = letters, RoundedCornerShape(12.dp))
                    }
                }

                Divider(modifier = Modifier.padding(top = 60.dp, bottom = 60.dp))

                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {

                    // THEME
                    Text(text = stringResource(R.string.theme))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectableGroup()
                    ) {
                        viewModel.themeRadioOptions.forEach { theme ->
                            Selectable(
                                selectableOption = theme,
                                selected = (theme == viewModel.themeSelectedOption.value)
                            ) {
                                viewModel.onThemeOptionSelected(theme)
                            }
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
                            Selectable(
                                selectableOption = shape,
                                selected = (shape == viewModel.shapeSelectedOption.value)
                            ) {
                                viewModel.onShapeOptionSelected(shape)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(60.dp))

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /*TODO*/ }
                    ) {
                        Text(stringResource(R.string.apply_icons))
                    }

                }
            }
        }
    }
}

@Composable
private fun Selectable(
    selectableOption: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        Modifier.selectable(
            selected = selected,
            onClick = onClick, // TODO: Change Theme and Shape of icons
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
fun CustomIcon(letters: String, cornerShape: RoundedCornerShape) {
    Box(
        modifier = Modifier
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
        LeIconPackApp()
    }
}
