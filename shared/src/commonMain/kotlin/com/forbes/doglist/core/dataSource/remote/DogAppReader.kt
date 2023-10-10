package com.forbes.doglist.core.dataSource.remote

import com.forbes.doglist.core.models.DogBreed

class DogAppReader internal constructor(
    private val dogAppLoader: DogAppLoader
) {
    @Throws(Exception::class)
    suspend fun getDogList(): ApiResult<List<DogBreed>> =
        dogAppLoader.getDogList()

    @Throws(Exception::class)
    suspend fun getDogImages(breed: String): List<String> =
        dogAppLoader.getDogImages(breed)

    companion object
}
