package com.audienix.doglist.android.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.audienix.doglist.android.R
import com.audienix.doglist.android.ui.theme.MaterialColorPalette

/**
 * Composable for displaying a progress loading indicator.
 *
 * @author Arighna Maity
 */
@Composable
fun ProgressLoadingIndicator() {
    val animation = rememberInfiniteTransition(label = "")
    val progress by animation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Restart,
        ), label = ""
    )
    Box(
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.dimension_64dp))
            .scale(progress)
            .alpha(1f - progress)
            .border(
                dimensionResource(id = R.dimen.dimension_4dp),
                color = MaterialColorPalette.primary,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.dimension_8dp)),
            text = "\uD83D\uDC36",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialColorPalette.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}