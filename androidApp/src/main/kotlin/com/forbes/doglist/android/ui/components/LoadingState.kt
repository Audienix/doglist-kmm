package com.forbes.doglist.android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.forbes.doglist.android.R
import com.forbes.doglist.android.ui.theme.MaterialColorPalette

@Composable
fun LoadingState(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp)),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.dimension_8dp),
            Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProgressLoadingIndicator()
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
        Text(
            text = stringResource(id = R.string.progress_loader_text),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialColorPalette.onSurfaceVariant
        )
    }
}