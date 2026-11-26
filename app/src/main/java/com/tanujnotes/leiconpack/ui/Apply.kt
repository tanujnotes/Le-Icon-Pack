package com.tanujnotes.leiconpack.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tanujnotes.leiconpack.R
import com.tanujnotes.leiconpack.ui.theme.LeIconPackTheme

private val ApplySteps = listOf(
    R.string.apply_step_one,
    R.string.apply_step_two,
    R.string.apply_step_three,
    R.string.apply_step_four,
    R.string.apply_step_five,
)

/** Manual steps, shown when the default launcher can't be asked to apply the icon pack directly. */
@Composable
fun ApplyScreen(onBack: () -> Unit) {
    LettersScreen(
        topBar = { BackTopBar(onBack = onBack) },
        bottomBar = {
            BottomBar {
                PrimaryButton(text = stringResource(R.string.apply_done), onClick = onBack)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ScreenHeader(
                title = stringResource(R.string.apply_title),
                description = stringResource(R.string.apply_description)
            )
            Column(
                modifier = Modifier.padding(start = ScreenPadding, top = 32.dp, end = ScreenPadding),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                ApplySteps.forEachIndexed { index, step ->
                    ApplyStep(number = index + 1, step = step)
                }
            }
            Text(
                text = stringResource(R.string.apply_note),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = ScreenPadding, top = 28.dp, end = ScreenPadding)
            )
            DayNightNote(
                modifier = Modifier.padding(
                    start = ScreenPadding,
                    top = 12.dp,
                    end = ScreenPadding,
                    bottom = 16.dp
                )
            )
        }
    }
}

@Composable
private fun ApplyStep(number: Int, @StringRes step: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(9.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = stringResource(step),
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 17.sp),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ApplyScreenPreview() {
    LeIconPackTheme {
        Surface {
            ApplyScreen(onBack = {})
        }
    }
}
