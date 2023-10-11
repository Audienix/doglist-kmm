package com.forbes.doglist.core.dataSource.remote

import com.forbes.doglist.core.models.DogBreed

/**
 * A class for reading dog-related data using a [DogAppLoader].
 *
 * @param dogAppLoader The loader for fetching dog-related data.
 * @constructor Creates a [DogAppReader] instance.
 * @author Arighna Maity
 */
class DogAppReader internal constructor(
    private val dogAppLoader: DogAppLoader
) {
    /**
     * Get a list of dog breeds.
     *
     * @return An [ApiResult] containing a list of dog breeds.
     * @throws Exception if there's an error during the data retrieval.
     */
    @Throws(Exception::class)
    suspend fun getDogList(): ApiResult<List<DogBreed>> =
        dogAppLoader.getDogList()

    /**
     * Get a list of dog images for a specific breed.
     *
     * @param breed The name of the dog breed.
     * @return A list of URLs for dog images.
     * @throws Exception if there's an error during the data retrieval.
     */
    @Throws(Exception::class)
    suspend fun getDogImages(breed: String): ApiResult<List<String>> =
        dogAppLoader.getDogImages(breed)

    companion object
}
