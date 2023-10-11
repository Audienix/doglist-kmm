package com.forbes.doglist.core.models

/**
 * Data class representing a dog breed.
 *
 * @property name The name of the dog breed.
 * @property subBreeds The sub-breeds as a comma-separated string.
 * @property imageUrl The URL of the dog's image.
 * @constructor Creates a [DogBreed] instance.
 * @author Arighna Maity
 */
data class DogBreed(
    val name: String,
    val subBreeds: String,
    val imageUrl: String
)

/**
 * Data class representing a dog breed with sub-breeds.
 *
 * @property name The name of the dog breed.
 * @property subBreeds The sub-breeds as a comma-separated string.
 * @constructor Creates a [DogBreedWithSubBreeds] instance.
 * @author Arighna Maity
 */
data class DogBreedWithSubBreeds(
    val name: String,
    val subBreeds: String
)
