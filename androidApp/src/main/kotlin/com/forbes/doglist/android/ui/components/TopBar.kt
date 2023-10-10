package com.forbes.doglist.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.forbes.doglist.android.R
import com.forbes.doglist.android.ui.theme.MaterialColorPalette

/**
 * Composable for TopBar of the DogList Home screen.
 *
 * @author Arighna Maity
 */
@Composable
fun TopBar() {
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(color = MaterialColorPalette.surface)
            .padding(dimensionResource(id = R.dimen.dimension_16dp)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
    ) {
        Text(
            text = stringResource(id = R.string.home_title),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialColorPalette.onSurface
        )
        Text(
            text = stringResource(id = R.string.home_sub_title),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialColorPalette.onSurfaceVariant
        )
    }
}
