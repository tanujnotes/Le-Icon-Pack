package com.tanujnotes.leiconpack.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tanujnotes.leiconpack.R

private val ApplySteps = listOf(
    R.string.apply_step_one,
    R.string.apply_step_two,
    R.string.apply_step_three,
    R.string.apply_step_four,
    R.string.apply_step_five,
)

@Composable
fun ReviewDialog(onRate: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onRate) {
                Text(text = stringResource(R.string.rate_now).uppercase())
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.no_thanks).uppercase())
            }
        },
        title = {
            Text(
                text = stringResource(R.string.rate_this_app),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(text = stringResource(R.string.rate_review_message), fontSize = 14.sp)
        }
    )
}

/** Manual steps, shown when the default launcher can't be asked to apply the icon pack directly. */
@Composable
fun ApplyInstructionsDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.close_label).uppercase())
            }
        },
        title = {
            Text(
                text = stringResource(R.string.applying_icons_label),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ApplySteps.forEachIndexed { index, step ->
                    ApplyInstruction(number = index + 1, instruction = step)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = stringResource(R.string.apply_note))
            }
        }
    )
}

@Composable
private fun ApplyInstruction(number: Int, @StringRes instruction: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color = MaterialTheme.colorScheme.secondary, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = number.toString(), color = MaterialTheme.colorScheme.onSecondary)
        }
        Text(text = stringResource(instruction))
    }
}
