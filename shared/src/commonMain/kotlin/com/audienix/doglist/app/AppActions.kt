package com.audienix.doglist.app

import com.audienix.doglist.core.dataSource.remote.ApiResult
import com.audienix.doglist.core.models.DogBreed

/**
 * A sealed class representing all actions for the Dog List app.
 *
 * @author Arighna Maity
 */
sealed class AppActions : Action {
    data object FetchDogList : AppActions()
    data class DisplayDogList(val listApiResult: ApiResult<List<DogBreed>>) : AppActions()
    data class FetchDogImages(val breed: String) : AppActions()
    data class DisplayDogImages(val imageList: ApiResult<List<String>>) : AppActions()
    data class Error(val error: Exception) : AppActions()
}
