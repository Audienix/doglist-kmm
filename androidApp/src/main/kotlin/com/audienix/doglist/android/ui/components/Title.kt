package com.audienix.doglist.android.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.audienix.doglist.android.ui.theme.MaterialColorPalette

/**
 * Composable for displaying a title text.
 *
 * @param title The title text to be displayed.
 * @author Arighna Maity
 */
@Composable
fun Title(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 0.dp, 0.dp),
        color = MaterialColorPalette.primary,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.W600,
        textAlign = TextAlign.Start
    )
}