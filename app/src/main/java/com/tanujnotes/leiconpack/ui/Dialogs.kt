package com.tanujnotes.leiconpack.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tanujnotes.leiconpack.R

@Composable
fun ReviewDialog(onRate: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onRate) {
                Text(text = stringResource(R.string.rate_now))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.no_thanks))
            }
        },
        title = {
            Text(text = stringResource(R.string.rate_this_app))
        },
        text = {
            Text(text = stringResource(R.string.rate_review_message))
        }
    )
}
