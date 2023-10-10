package com.forbes.doglist.app

import com.forbes.doglist.core.dataSource.remote.ApiResult
import com.forbes.doglist.core.models.DogBreed

sealed class AppActions : Action {
    data object FetchDogList : AppActions()
    data class DisplayDogList(val listApiResult: ApiResult<List<DogBreed>>) : AppActions()
    data class FetchDogImages(val breed: String) : AppActions()
    data class DisplayDogImages(val imageList: List<String>) : AppActions()
    data class Error(val error: Exception) : AppActions()
}
