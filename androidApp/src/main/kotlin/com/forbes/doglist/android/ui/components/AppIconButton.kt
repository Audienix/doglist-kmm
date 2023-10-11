package com.forbes.doglist.android.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.forbes.doglist.android.ui.theme.MaterialColorPalette

@Composable
fun AppIconButton(imageVector: ImageVector, onIconClick: () -> Unit) {
    IconButton(onClick = { onIconClick() }) {
        Icon(
            imageVector,
            null,
            tint = MaterialColorPalette.onSurfaceVariant
        )
    }
}
