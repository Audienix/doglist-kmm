package com.audienix.doglist.android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.audienix.doglist.android.R
import com.audienix.doglist.android.ui.theme.MaterialColorPalette

/**
 * Composable for displaying a top app bar with a title and an optional navigation icon.
 *
 * @param title The title to be displayed in the top app bar.
 * @param navIcon An optional composable for the navigation icon.
 * @author Arighna Maity
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    navIcon: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialColorPalette.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialColorPalette.surfaceContainerLow,
            titleContentColor = MaterialColorPalette.onSurface,
        ),
        navigationIcon = navIcon
    )

}
