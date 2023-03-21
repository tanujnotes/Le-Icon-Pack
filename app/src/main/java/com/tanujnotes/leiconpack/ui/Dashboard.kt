package com.tanujnotes.leiconpack.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tanujnotes.leiconpack.CustomIcon
import com.tanujnotes.leiconpack.R
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
    val showDimensionsDialog = remember { mutableStateOf(false) }
   // val showReportDialog = remember { mutableStateOf(false) }
    val iconsLabel = viewModel.lettersList
    Box(modifier = Modifier.fillMaxSize()) {
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
                        contentAlignment = Alignment.Center
                    ) {
                      /* com.google.accompanist.flowlayout.FlowRow(
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
                        }*/
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp)
                        ) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround) {
                            iconsLabel.subList(0,4).forEach { letters ->
                                CustomIcon(letters = letters, RoundedCornerShape(12.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround) {
                            iconsLabel.subList(4,8).forEach { letters ->
                                CustomIcon(letters = letters, RoundedCornerShape(12.dp))
                            }
                        }
                    }
                    }



                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(id = string.app_name),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(string.app_description),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.inter)),
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
                            Icon(painter = painterResource(id = drawable.ic_star), contentDescription = null)
                            Text(
                                text = stringResource(string.rate_review_label).uppercase(),
                                fontFamily = FontFamily(Font(R.font.inter)),
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
                        showDimensionsDialog.value = true
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
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = stringResource(string.icon_dimension_label),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
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
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 30.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Column {
                        Text(
                            text = stringResource(string.custom_icons_count),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = stringResource(string.why_icons_label),
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(20.dp))
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
                Icon(painter = painterResource(id = drawable.ic_report) , contentDescription = null )
                Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = stringResource(string.report_missing_label),
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
            }
            Spacer(modifier = Modifier.height(100.dp))
           /* if (showReportDialog.value){
                AlertDialog(
                    onDismissRequest = { showReportDialog.value = false },
                    confirmButton = {
                                    Button(onClick = {
                                        showReportDialog.value = false
                                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                            data = Uri.parse("mailto:")
                                            putExtra(Intent.EXTRA_EMAIL, arrayOf("leiconpack@gmail.com"))
                                            putExtra(Intent.EXTRA_SUBJECT,"Missing Icon Report")
                                            putExtra(Intent.EXTRA_TEXT, "Manufacturer : ${Build.MANUFACTURER.uppercase()}\n" +
                                                    "Model: ${Build.MODEL.uppercase()}\n\n App Name:")
                                        }
                                        try {
                                            context.startActivity(Intent.createChooser(emailIntent, "Choose email app"))

                                        }catch (e:ActivityNotFoundException){
                                            Toast.makeText(
                                                context,
                                                "No email activity found",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    }) {
                                        Text(
                                            text = stringResource(string.report_label),
                                            fontFamily = FontFamily(Font(R.font.inter)),)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Icon(
                                            imageVector = Icons.Default.Send,
                                            contentDescription = "report icons"
                                        )
                                    }
                    },
                    title = { Text(
                        text = "Missing Icons",
                        fontFamily = FontFamily(Font(R.font.inter)),
                    ) },
                    text = {
                        Column {
                            Text(
                                text = "Please provide us with the following in your request:",
                                fontFamily = FontFamily(Font(R.font.inter)),
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            ReportInfoItem("Device Model")
                            Spacer(modifier = Modifier.height(10.dp))
                            ReportInfoItem("Device Manufacturer")
                            Spacer(modifier = Modifier.height(10.dp))
                            ReportInfoItem("App Name")
                            Spacer(modifier = Modifier.height(10.dp))
                            ReportInfoItem("Playstore link for the app (If possible)")
                        }
                    }
                )
            }*/
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
fun ReportInfoItem(info:String) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(
            modifier = Modifier
                .padding(top = 5.dp)
                .size(8.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = CircleShape
                )
        )
        Text(
            text = info,
            fontFamily = FontFamily(Font(R.font.inter)),
        )
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
            Text(
                text = step,
                fontFamily = FontFamily(Font(R.font.inter)),
                color = MaterialTheme.colorScheme.onSecondary)
        }
        Text(
            text = instruction,
            fontFamily = FontFamily(Font(R.font.inter)),
        )
    }
}
