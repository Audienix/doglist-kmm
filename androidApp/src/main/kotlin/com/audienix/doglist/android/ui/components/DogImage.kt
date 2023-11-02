package com.audienix.doglist.android.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.audienix.doglist.android.R
import com.audienix.doglist.android.ui.screens.DogImagePreviewScreen

/**
 * Composable for displaying a dog image preview.
 *
 * @param modifier The modifier for customizing the layout of the composable.
 * @param imageUrl The URL of the dog image to be displayed.
 * @param dogBreed The breed of the dog for content description.
 * @param contentScale The scale to be used for displaying the image.
 * @author Arighna Maity
 */
@Composable
fun DogImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    dogBreed: String,
    contentScale: ContentScale = ContentScale.Crop
) {
    val model = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .error(R.drawable.no_dog)
        .scale(Scale.FIT)
        .crossfade(true)
        .build()
    val painter = rememberAsyncImagePainter(model = model)
    val state = painter.state
    val navigator = LocalNavigator.currentOrThrow
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.clickable {
            navigator.push(
                item = DogImagePreviewScreen(
                    imageUrl = imageUrl,
                    dogBreed = dogBreed
                )
            )
        }
    ) {
        AnimatedVisibility(visible = (state is AsyncImagePainter.State.Loading)) {
            ProgressLoadingIndicator()
        }
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.content_desc_image, dogBreed),
            contentScale = contentScale,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
