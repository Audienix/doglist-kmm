package com.forbes.doglist.android.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun getGridCellCount(
    configuration: Configuration
): Int {
    // Detect screen orientation and set the appropriate number of cells
    var gridCells by remember { mutableIntStateOf(1) }

    gridCells = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 2
        Configuration.ORIENTATION_PORTRAIT -> 1
        else -> 1
    }
    return gridCells
}