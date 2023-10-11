package com.forbes.doglist.android.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.forbes.doglist.android.ui.theme.MaterialColorPalette

/**
 * Composable for displaying an icon button.
 *
 * @param imageVector The image vector to be displayed as an icon.
 * @param onIconClick The lambda to be invoked when the icon button is clicked.
 * @author Arighna Maity
 */
@Composable
fun AppIconButton(imageVector: ImageVector, onIconClick: () -> Unit) {
    IconButton(onClick = { onIconClick() }) {
        Icon(
            imageVector,
            contentDescription = imageVector.name,
            tint = MaterialColorPalette.onSurfaceVariant
        )
    }
}
