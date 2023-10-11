package com.forbes.doglist.android.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration

/**
 * Get the number of grid cells based on the screen orientation.
 *
 * @return The number of grid cells.
 * @author Arighna Maity
 */
@Composable
fun getGridCellCount(): Int {
    val configuration = LocalConfiguration.current
    // Detect screen orientation and set the appropriate number of cells
    var gridCells by remember { mutableIntStateOf(1) }

    gridCells = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 2
        Configuration.ORIENTATION_PORTRAIT -> 1
        else -> 1
    }
    return gridCells
}

/**
 * Get the appropriate `ContentScale` based on the screen orientation.
 *
 * @return The content scale based on the screen orientation.
 * @author Arighna Maity
 */
@Composable
fun getImageContentScale(): ContentScale {
    val configuration = LocalConfiguration.current
    var contentScale by remember { mutableStateOf(ContentScale.Crop) }
    contentScale = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> ContentScale.FillHeight
        Configuration.ORIENTATION_PORTRAIT -> ContentScale.FillWidth
        else -> ContentScale.FillWidth
    }
    return contentScale
}
