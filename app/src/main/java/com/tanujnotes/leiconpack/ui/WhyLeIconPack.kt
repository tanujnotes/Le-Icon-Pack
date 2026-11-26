package com.tanujnotes.leiconpack.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
fun WhyLeIconPack() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 20.dp, top = 20.dp, end = 20.dp)
    ) {
        items(items = Features) { feature ->
            FeatureItem(title = stringResource(feature.title), desc = stringResource(feature.description))
        }
    }
}

@Composable
private fun FeatureItem(title: String, desc: String) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.onSecondary,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(10.dp)
    )
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .padding(top = 5.dp)
                .size(10.dp)
                .background(color = MaterialTheme.colorScheme.onSurface, shape = CircleShape)
        )
        Text(text = desc)
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WhyLeIconPackPreview() {
    LeIconPackTheme {
        WhyLeIconPack()
    }
}
