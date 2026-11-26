package com.tanujnotes.leiconpack.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tanujnotes.leiconpack.R
import com.tanujnotes.leiconpack.ui.theme.LeIconPackTheme
import com.tanujnotes.leiconpack.util.openGitHub
import com.tanujnotes.leiconpack.util.openPlayStore
import com.tanujnotes.leiconpack.util.openPlayStoreDevPage
import com.tanujnotes.leiconpack.util.openShareIntent

/**
 * Real icons from the pack, two rows of four, for apps nearly everyone has. Being the pack's own
 * drawables, they switch between day and night like the pack does.
 */
private val SampleIcons = listOf(
    R.drawable.ca, R.drawable.cl, R.drawable.ma, R.drawable.ph,
    R.drawable.me, R.drawable.mu, R.drawable.no, R.drawable.se,
)

@Composable
fun HomeScreen(
    onApplyClick: () -> Unit,
    onWhyLettersClick: () -> Unit,
    onReportMissingClick: () -> Unit,
) {
    val context = LocalContext.current
    LettersScreen(
        topBar = {
            TopBar(horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { context.openGitHub() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_github),
                        contentDescription = stringResource(R.string.cd_github)
                    )
                }
                IconButton(onClick = { context.openShareIntent() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = stringResource(R.string.cd_share)
                    )
                }
            }
        },
        bottomBar = {
            BottomBar {
                Text(
                    text = stringResource(R.string.digital_minimalism),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(role = Role.Button) { context.openPlayStoreDevPage() }
                        .heightIn(min = 44.dp)
                        .wrapContentHeight()
                )
                PrimaryButton(text = stringResource(R.string.apply_label), onClick = onApplyClick)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(start = ScreenPadding, top = 12.dp, end = ScreenPadding),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.home_title),
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = stringResource(R.string.app_description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            SampleIconGrid()
            Spacer(modifier = Modifier.height(28.dp))
            InsetDivider()
            LinkRow(
                text = stringResource(R.string.why_icons_label),
                icon = R.drawable.ic_chevron_right,
                onClick = onWhyLettersClick
            )
            InsetDivider()
            LinkRow(
                text = stringResource(R.string.report_missing_label),
                icon = R.drawable.ic_chevron_right,
                onClick = onReportMissingClick
            )
            InsetDivider()
            LinkRow(
                text = stringResource(R.string.rate_review_label),
                icon = R.drawable.ic_arrow_outward,
                onClick = { context.openPlayStore() }
            )
            InsetDivider()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SampleIconGrid() {
    Column(
        modifier = Modifier.padding(horizontal = ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(24.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SampleIcons.chunked(4).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    row.forEach { icon ->
                        Image(
                            painter = painterResource(icon),
                            // Decorative: the caption below says what they are.
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    }
                }
            }
        }
        Text(
            text = stringResource(R.string.home_icon_caption),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = stringResource(R.string.day_night_note),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    LeIconPackTheme {
        Surface {
            HomeScreen(onApplyClick = {}, onWhyLettersClick = {}, onReportMissingClick = {})
        }
    }
}

// A night uiMode (not just a dark theme), so the pack's night icons are the ones shown.
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenDarkPreview() {
    LeIconPackTheme {
        Surface {
            HomeScreen(onApplyClick = {}, onWhyLettersClick = {}, onReportMissingClick = {})
        }
    }
}
