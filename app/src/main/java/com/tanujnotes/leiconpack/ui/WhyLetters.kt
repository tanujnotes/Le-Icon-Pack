package com.tanujnotes.leiconpack.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tanujnotes.leiconpack.R
import com.tanujnotes.leiconpack.ui.theme.LeIconPackTheme

private data class Feature(@StringRes val title: Int, @StringRes val description: Int)

private val Features = listOf(
    Feature(R.string.feature_screen_time_title, R.string.feature_screen_time_desc),
    Feature(R.string.feature_icon_count_title, R.string.feature_icon_count_desc),
    Feature(R.string.feature_consistent_title, R.string.feature_consistent_desc),
    Feature(R.string.feature_cognitive_load_title, R.string.feature_cognitive_load_desc),
)

@Composable
fun WhyLettersScreen(onBack: () -> Unit) {
    LettersScreen(topBar = { BackTopBar(onBack = onBack) }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                ScreenHeader(title = stringResource(R.string.why_icons_label))
                Spacer(modifier = Modifier.height(28.dp))
            }
            items(items = Features) { feature ->
                InsetDivider()
                FeatureItem(feature)
            }
            item { InsetDivider() }
        }
    }
}

@Composable
private fun FeatureItem(feature: Feature) {
    Column(
        modifier = Modifier.padding(horizontal = ScreenPadding, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text = stringResource(feature.title), style = MaterialTheme.typography.titleLarge)
        Text(
            text = stringResource(feature.description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun WhyLettersScreenPreview() {
    LeIconPackTheme {
        Surface {
            WhyLettersScreen(onBack = {})
        }
    }
}
