package com.forbes.doglist.android.ui.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.forbes.doglist.android.R

@Composable
fun DogImagePreview(
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
                item = ZoomableImage(
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

class ZoomableImage(
    private val imageUrl: String,
    private val dogBreed: String
) : Screen {
    @Composable
    override fun Content() {
        val configuration = LocalConfiguration.current
        var contentScale by remember { mutableStateOf(ContentScale.Crop) }

        contentScale = when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> ContentScale.FillHeight
            Configuration.ORIENTATION_PORTRAIT -> ContentScale.FillWidth
            else -> ContentScale.FillWidth
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DogImagePreview(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
                dogBreed = dogBreed,
                imageUrl = imageUrl,
                contentScale = contentScale
            )
            val navigator: Navigator = LocalNavigator.currentOrThrow
            AppIconButton(imageVector = Icons.Filled.Close) { navigator.pop() }
        }
    }
}
