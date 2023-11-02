package com.audienix.doglist.android.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.audienix.doglist.android.R
import com.audienix.doglist.android.ui.theme.MaterialColorPalette
import com.audienix.doglist.core.models.DogBreed
import com.google.accompanist.pager.HorizontalPagerIndicator

/**
 * Composable for displaying a carousel of dog images.
 *
 * @param modifier The modifier for customizing the layout of the composable.
 * @param value The list of image URLs to be displayed in the carousel.
 * @param breed The dog breed associated with the images.
 * @author Arighna Maity
 */
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun DogImageCarousel(
    modifier: Modifier = Modifier,
    value: List<String>,
    breed: DogBreed
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { value.size }
    )
    Box(modifier = modifier) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f),
            state = pagerState
        ) { page ->
            DogImage(
                dogBreed = breed.name,
                imageUrl = value[page]
            )
        }
        if (value.size > 1) {
            HorizontalPagerIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(dimensionResource(id = R.dimen.dimension_16dp)),
                pageCount = value.size,
                pagerState = pagerState,
                activeColor = MaterialColorPalette.surfaceContainerLowest,
            )
        }
    }
}