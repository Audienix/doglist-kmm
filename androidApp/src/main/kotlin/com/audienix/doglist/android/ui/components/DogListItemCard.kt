package com.audienix.doglist.android.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.audienix.doglist.android.R
import com.audienix.doglist.android.ui.theme.MaterialColorPalette
import com.audienix.doglist.android.ui.theme.Shapes
import com.audienix.doglist.core.models.DogBreed
import com.audienix.doglist.util.extensions.capitalizeFirstLetter

/**
 * Composable for each dog breed item in the list.
 *
 * @author: Arighna Maity
 */
@Composable
fun DogListItemCard(dog: DogBreed, onItemClicked: (dogBreed: DogBreed) -> Unit) {
    Card(
        border = BorderStroke(
            dimensionResource(id = R.dimen.dimension_stroke_width_low),
            MaterialColorPalette.surfaceContainerHigh
        ),
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialColorPalette.surfaceContainerLow),
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

            DogImage(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dimension_64dp))
                    .clip(Shapes.medium),
                dogBreed = dog.name,
                imageUrl = dog.imageUrl
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
                SubBreed(dog)
            }
        }
    }
}

/**
 * Composable for showing all available sub-breeds of a dog breed.
 *
 * @author: Arighna Maity
 */
@Composable
private fun SubBreed(dog: DogBreed) {
    var subBreed = stringResource(
        R.string.sub_breeds,
        dog.subBreeds
    )
    if (dog.subBreeds.isEmpty())
        subBreed = stringResource(id = R.string.no_sub_breeds)

    val subBreedsList = subBreed.split(", ").toMutableList()

    if (subBreedsList.size > 1) {
        val nMore = subBreedsList.size - 1
        val formattedText = buildAnnotatedString {
            append(subBreedsList[0])
            append(" ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(stringResource(R.string.more_ellipsis, nMore))
            }
        }
        Text(
            text = formattedText,
            style = typography.bodyMedium,
            color = MaterialColorPalette.onSurfaceVariant
        )
    } else {
        Text(
            text = subBreed,
            style = typography.bodyMedium,
            color = MaterialColorPalette.onSurfaceVariant
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