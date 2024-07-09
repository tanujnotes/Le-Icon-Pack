package com.tanujnotes.leiconpack.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tanujnotes.leiconpack.CustomIcon
import com.tanujnotes.leiconpack.R
import com.tanujnotes.leiconpack.R.drawable
import com.tanujnotes.leiconpack.R.string
import com.tanujnotes.leiconpack.applyIcons
import com.tanujnotes.leiconpack.util.Extensions.openPlayStore
import com.tanujnotes.leiconpack.util.Extensions.openPlayStoreDevPage
import com.tanujnotes.leiconpack.util.Extensions.openShareIntent
import com.tanujnotes.leiconpack.util.MenuItem


@Composable
fun Dashboard(
    navController: NavController,
    viewModel: MainViewModel,
    selectedItem: MutableState<MenuItem>,
    showApplyDialog: MutableState<Boolean>,
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val showDimensionsDialog = remember { mutableStateOf(false) }
    val iconsLabel = viewModel.lettersList
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
                .align(Alignment.TopCenter)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                iconsLabel.subList(0, 4).forEach { letters ->
                                    CustomIcon(letters = letters, RoundedCornerShape(16.dp))
                                }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                iconsLabel.subList(4, 8).forEach { letters ->
                                    CustomIcon(letters = letters, RoundedCornerShape(16.dp))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(id = string.app_name),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(string.app_description),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .clickable {
                                    context.openPlayStore()
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = drawable.ic_star),
                                contentDescription = null
                            )
                            Text(
                                text = stringResource(string.rate_review_label),
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        IconButton(onClick = {
                            context.openShareIntent()
                        }) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "share")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    onClick = {
                        showDimensionsDialog.value = true
                    },
                    contentPadding = PaddingValues(16.dp/*horizontal = 20.dp, vertical = 20.dp*/),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Image(
                                painter = painterResource(id = drawable.ch),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                            Text(
                                text = stringResource(id = string.chrome_app_label),
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(string.icon_dimension_label),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
                if (showDimensionsDialog.value) {
                    AlertDialog(
                        onDismissRequest = { showDimensionsDialog.value = false },
                        confirmButton = {
                            TextButton(onClick = {
                                showDimensionsDialog.value = false
                                selectedItem.value = MenuItem.Home
                            }) {
                                Text(
                                    text = stringResource(string.close_label).uppercase(),
                                    fontFamily = FontFamily(Font(R.font.inter))
                                )
                            }
                        },
                        title = {
                            Text(
                                text = stringResource(string.chrome_app_label),
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        text = {
                            Box(
                                modifier = Modifier
                                    .padding(10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = drawable.ch),
                                    contentDescription = null,
                                    modifier = Modifier.size(100.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                        }
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        navController.navigate(
                            route = MenuItem.WhyLeIcons.title.lowercase().trim()
                        )
                    },
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Column(modifier = Modifier) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(string.custom_icons_count),
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                            )
                            Text(
                                text = stringResource(id = string.icons_label),
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontSize = 18.sp,
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = stringResource(string.why_icons_label),
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    navController.navigate(route = MenuItem.IconRequest.title.trim().lowercase())
                },
                contentPadding = PaddingValues(vertical = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(painter = painterResource(id = drawable.ic_report), contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(string.report_missing_label),
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    context.openPlayStoreDevPage()
                },
                contentPadding = PaddingValues(vertical = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    text = stringResource(string.digital_minimalism),
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.height(100.dp))
            if (showApplyDialog.value) {
                AlertDialog(
                    onDismissRequest = { showApplyDialog.value = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showApplyDialog.value = false
                        }) {
                            Text(
                                text = stringResource(string.close_label).uppercase(),
                                fontFamily = FontFamily(Font(R.font.inter))
                            )
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(string.applying_icons_label),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.inter))
                        )
                    },
                    text = {
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            ApplyInstruction(
                                step = "1",
                                instruction = stringResource(string.apply_step_one)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            ApplyInstruction(
                                step = "2",
                                instruction = stringResource(string.apply_step_two)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            ApplyInstruction(
                                step = "3",
                                instruction = stringResource(string.apply_step_three)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            ApplyInstruction(
                                step = "4",
                                instruction = stringResource(string.apply_step_four)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            ApplyInstruction(
                                step = "5",
                                instruction = stringResource(string.apply_step_five)
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(

                                text = stringResource(string.apply_note),
                                fontFamily = FontFamily(Font(R.font.inter))
                            )

                        }
                    }
                )
            }
        }

        Button(
            onClick = {
                applyIcons(context, showApplyDialog)
            },
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Icon(imageVector = Icons.Default.Done, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(string.apply_label),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.inter))
            )
        }
    }

}

@Composable
private fun ApplyInstruction(step: String, instruction: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = step,
                fontFamily = FontFamily(Font(R.font.inter)),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Text(
            text = instruction,
            fontFamily = FontFamily(Font(R.font.inter)),
        )
    }
}
