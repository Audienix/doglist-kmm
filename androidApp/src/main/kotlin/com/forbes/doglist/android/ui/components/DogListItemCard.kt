package com.forbes.doglist.android.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.forbes.doglist.android.R
import com.forbes.doglist.android.ui.theme.MaterialColorPalette
import com.forbes.doglist.android.ui.theme.Shapes
import com.forbes.doglist.core.models.DogBreed
import com.forbes.doglist.util.extensions.capitalizeFirstLetter

/**
 * Composables for each dog breed item in the list.
 *
 * @author: Arighna Maity
 */
@Composable
fun DogListItemCard(dog: DogBreed, onItemClicked: (dog: DogBreed) -> Unit) {
    Card(
        border = BorderStroke(
            dimensionResource(id = R.dimen.dimension_stroke_width_low),
            MaterialColorPalette.surfaceContainer
        ),
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialColorPalette.surfaceContainerLowest),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dimension_4dp))
            .fillMaxWidth()
            .clickable(onClick = { onItemClicked(dog) })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.dimension_16dp)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
        ) {

            DogImagePreview(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dimension_64dp))
                    .clip(Shapes.medium), dog = dog
            )
            Column(
                modifier = Modifier.align(Alignment.Top),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_12dp))
            ) {
                Text(
                    text = dog.name.capitalizeFirstLetter(),
                    color = MaterialColorPalette.onSurface,
                    style = typography.titleMedium
                )
                var subBreed = stringResource(
                    R.string.sub_breeds,
                    dog.subBreeds
                )
                if (dog.subBreeds.isEmpty())
                    subBreed = stringResource(id = R.string.no_sub_breeds)
                Text(
                    text = subBreed,
                    style = typography.bodyMedium,
                    color = MaterialColorPalette.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun DogImagePreview(
    modifier: Modifier = Modifier, dog: DogBreed
) {
    val model = ImageRequest.Builder(LocalContext.current)
        .data(dog.imageUrl)
        .error(R.drawable.no_dog)
        .crossfade(true)
        .build()
    val painter = rememberAsyncImagePainter(model = model)
    val state = painter.state
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        AnimatedVisibility(visible = (state is AsyncImagePainter.State.Loading)) {
            ProgressLoadingIndicator()
        }
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.content_desc_image, dog.name),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun ItemDogCardPreview() {
    val dog = DogBreed(
        name = "BullDog",
        subBreeds = stringResource(id = R.string.no_sub_breeds),
        imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZu_azQlu1zOhznps9QEWPBEnwWqNEShGlXw&usqp=CAU"
    )
    DogListItemCard(dog = dog) {}
}