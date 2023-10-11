package com.forbes.doglist.core.dataSource.remote


import com.forbes.doglist.core.dataSource.remote.response.DogImageListResponse
import com.forbes.doglist.core.dataSource.remote.response.DogListResponse
import com.forbes.doglist.core.dataSource.remote.response.DogRandomImageResponse
import com.forbes.doglist.core.models.DogBreed
import com.forbes.doglist.core.models.DogBreedWithSubBreeds
import com.forbes.doglist.util.constants.URLConstants.BASE_URL
import com.forbes.doglist.util.constants.URLConstants.DOG_IMAGE_LIST_ENDPOINT
import com.forbes.doglist.util.constants.URLConstants.DOG_LIST_ENDPOINT
import com.forbes.doglist.util.constants.URLConstants.DOG_RANDOM_IMAGE_ENDPOINT
import com.forbes.doglist.util.extensions.asyncAll
import com.forbes.doglist.util.extensions.capitalizeFirstLetter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.path
import io.ktor.util.toLowerCasePreservingASCIIRules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

/**
 * An open class for loading dog-related data using an HTTP client.
 *
 * @param httpClient The HTTP client for making requests.
 * @constructor Creates a [DogAppLoader] instance.
 * @author Arighna Maity
 */
internal open class DogAppLoader(
    private val httpClient: HttpClient
) {
    /**
     * Get a list of dog breeds.
     *
     * @return An [ApiResult] containing a list of dog breeds.
     */
    suspend fun getDogList(): ApiResult<List<DogBreed>> {
        try {
            val dogBreedList = mutableListOf<DogBreed>()
            val api = httpClient.get(
                URLBuilder(Url(BASE_URL))
                    .apply {
                        path(DOG_LIST_ENDPOINT)
                    }.build()
            )
            val dogBreedNameWithSubBreedList = mutableListOf<DogBreedWithSubBreeds>()
            if (api.status == HttpStatusCode.OK) {
                val body = api.body<DogListResponse>()
                // Process the dog breed data
                body.message.entries.forEach { dogMap ->
                    dogBreedNameWithSubBreedList.add(
                        //Joining all the subbreeds by comma
                        DogBreedWithSubBreeds(
                            dogMap.key,
                            dogMap.value.joinToString(", ") { subBreed ->
                                subBreed.capitalizeFirstLetter()
                            }
                        )
                    )
                }
                // Prepare dog breed list with images asynchronously
                withContext(Dispatchers.IO) {
                    prepareDogsBreedListWithImage(
                        this,
                        dogBreedNameWithSubBreedList,
                        dogBreedList
                    )
                }
            } else
                return ApiResult.Error(errorCode = api.status.value)
            return ApiResult.Success(data = dogBreedList)
        } catch (ex: Exception) {
            return ApiResult.Error(errorCode = ex.hashCode())
        }
    }

    private suspend fun prepareDogsBreedListWithImage(
        scope: CoroutineScope,
        dogBreedNameWithSubBreedList: List<DogBreedWithSubBreeds>,
        dogBreedList: MutableList<DogBreed>,
    ) {
        var iterator = 0
        // Asynchronously fetch images for dog breeds
        scope.asyncAll(dogBreedNameWithSubBreedList) {
            val endPoint = DOG_RANDOM_IMAGE_ENDPOINT.replace("%s", it.name)
            httpClient.get(
                URLBuilder(Url(BASE_URL))
                    .apply {
                        path(endPoint)
                    }.build()
            )
        }
            .awaitAll() //Awaits for completion of given deferred values without blocking a thread and
            // resumes normally with the list of values when all deferred computations are complete.
            .forEach { breedImageResponse ->
                breedImageResponse.body<DogRandomImageResponse>().let { breedImage ->
                    dogBreedList.add(
                        DogBreed(
                            dogBreedNameWithSubBreedList[iterator].name.capitalizeFirstLetter(),
                            dogBreedNameWithSubBreedList[iterator].subBreeds,
                            breedImage.message
                        )
                    )
                }
                iterator++
            }
    }

    /**
     * Get a list of dog images for a specific breed.
     *
     * @param breed The name of the dog breed.
     * @return A list of URLs for dog images.
     */
    suspend fun getDogImages(breed: String): List<String> {
        try {
            var dogImages = listOf<String>()
            val endPoint =
                DOG_IMAGE_LIST_ENDPOINT.replace("%s", breed.toLowerCasePreservingASCIIRules())
            val api = httpClient.get(
                URLBuilder(Url(BASE_URL))
                    .apply {
                        path(endPoint)
                    }.build()
            )
            if (api.status == HttpStatusCode.OK) {
                val body = api.body<DogImageListResponse>()
                dogImages = body.message
            }
            return dogImages
        } catch (ex: Exception) {
            return listOf()
        }
    }
}