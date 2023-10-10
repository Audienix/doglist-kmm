package com.forbes.doglist.core.models

/**
 * Dog Breed data classes
 * @author: Arighna Maity
 */
data class DogBreed(
    val name: String,
    val subBreeds: String,
    val imageUrl: String
)

data class DogBreedWithSubBreeds(
    val name: String,
    val subBreeds: String
)
