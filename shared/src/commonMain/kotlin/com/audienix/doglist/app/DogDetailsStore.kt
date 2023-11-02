package com.audienix.doglist.app

import com.audienix.doglist.core.dataSource.remote.DogAppReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Data class representing the state of dog details in the app.
 *
 * @property progress Indicates if data is being loaded.
 * @property dogImages List of dog images.
 * @constructor Creates a [DogDetailsState] instance.
 * @author Arighna Maity
 */
data class DogDetailsState(
    val progress: Boolean,
    val dogImages: List<String>
) : State

/**
 * Store responsible for managing the state of dog details in the app.
 *
 * @param dogAppReader The reader for accessing dog-related data.
 * @constructor Creates a [DogDetailsStore] instance.
 * @author Arighna Maity
 */
class DogDetailsStore(
    private val dogAppReader: DogAppReader
) : Store<DogDetailsState, AppActions, SideEffect>,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val state = MutableStateFlow(DogDetailsState(false, emptyList()))
    private val sideEffect = MutableSharedFlow<SideEffect>()

    override fun observeState(): StateFlow<DogDetailsState> = state

    override fun observeSideEffect(): Flow<SideEffect> = sideEffect

    override fun dispatch(action: AppActions) {
        val oldState = state.value

        val newState = when (action) {
            is AppActions.FetchDogImages -> {
                if (oldState.progress) {
                    launch { sideEffect.emit(SideEffect.Error(Exception("In progress"))) }
                    oldState
                } else {
                    launch { loadDogImages(action.breed) }
                    oldState.copy(progress = true)
                }
            }

            is AppActions.DisplayDogImages -> {
                if (oldState.progress) {
                    val dogImages = action.imageList.data
                    dogImages?.let { images-> DogDetailsState(false, images) }
                } else {
                    launch { sideEffect.emit(SideEffect.Error(Exception("Unexpected action"))) }
                    oldState
                }
            }

            is AppActions.Error -> {
                if (oldState.progress) {
                    launch { sideEffect.emit(SideEffect.Error(action.error)) }
                    DogDetailsState(false, oldState.dogImages)
                } else {
                    launch { sideEffect.emit(SideEffect.Error(Exception("Unexpected action"))) }
                    oldState
                }
            }

            else -> { oldState}
        }

        if (newState != oldState) {
            if (newState != null) {
                state.value = newState
            }
        }
    }

    private suspend fun loadDogImages(breed: String) {
        try {
            val images = dogAppReader.getDogImages(breed)
            dispatch(AppActions.DisplayDogImages(images))
        } catch (e: Exception) {
            dispatch(AppActions.Error(e))
        }
    }
}
