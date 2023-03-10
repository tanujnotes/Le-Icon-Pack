package com.tanujnotes.leiconpack.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.tanujnotes.leiconpack.CustomIcon
import com.tanujnotes.leiconpack.R.drawable
import com.tanujnotes.leiconpack.R.string
import com.tanujnotes.leiconpack.applyIcons
import com.tanujnotes.leiconpack.util.MenuItem


@Composable
fun Dashboard(
    navController: NavController,
    viewModel: MainViewModel,
    selectedItem: MutableState<MenuItem>,
    showApplyDialog: MutableState<Boolean>
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val iconsLabel = viewModel.lettersList
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .verticalScroll(scrollState)
                .align(Alignment.TopCenter)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
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
                    ) {
                        com.google.accompanist.flowlayout.FlowRow(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            mainAxisSpacing = 10.dp,
                            crossAxisSpacing = 10.dp,
                            crossAxisAlignment = FlowCrossAxisAlignment.Center,
                            mainAxisAlignment = FlowMainAxisAlignment.Center
                        ) {
                            iconsLabel.forEach { letters ->
                                CustomIcon(letters = letters, RoundedCornerShape(12.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(id = string.app_name),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(string.app_description),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .clickable {
                                    val packageName = context.packageName
                                    try {
                                        context.startActivity(
                                            Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse("market://details?id=$packageName")
                                            )
                                        )
                                    } catch (e: ActivityNotFoundException) {
                                        context.startActivity(
                                            Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                                            )
                                        )
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(imageVector = Icons.Outlined.Star, contentDescription = null)
                            Text(
                                text = "Rate & Review".uppercase(),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        IconButton(onClick = {
                            val shareIntent = Intent.createChooser(Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT, "Check out this cool icon pack app. " +
                                            "https://play.google.com/store/apps/details?id=${context.packageName}"
                                )
                                type = "text/plain"
                            }, "Choose App")
                            context.startActivity(shareIntent)
                        }) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "share")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    onClick = {
                        showDialog.value = true
                    },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .weight(1f),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = drawable.ch),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = stringResource(id = string.chrome_app_label),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = stringResource(string.icon_dimension_label),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                    }
                }
                if (showDialog.value) {
                    AlertDialog(
                        onDismissRequest = { showDialog.value = false },
                        confirmButton = {
                            TextButton(onClick = {
                                showDialog.value = false
                                selectedItem.value = MenuItem.Home
                            }) {
                                Text(text = stringResource(string.close_label).uppercase())
                            }
                        },
                        title = {
                            Text(
                                text = stringResource(string.chrome_app_label),
                                fontSize = 18.sp,
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
                            route = MenuItem.CustomIcons.title.lowercase().trim()
                        )
                    },
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 30.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Column {
                        Text(
                            text = stringResource(string.custom_icons_count),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = stringResource(string.why_icons_label),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(100.dp))

            if (showApplyDialog.value) {
                AlertDialog(
                    onDismissRequest = { showApplyDialog.value = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showApplyDialog.value = false
                        }) {
                            Text(text = stringResource(string.close_label).uppercase())
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(string.applying_icons_label),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
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
                            Text(text = stringResource(string.apply_note))

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
                fontWeight = FontWeight.Bold
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
                .size(18.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = step, color = MaterialTheme.colorScheme.onSecondary)
        }
        Text(text = instruction)
    }
}
