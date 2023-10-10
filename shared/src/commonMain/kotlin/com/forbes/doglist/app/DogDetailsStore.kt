package com.forbes.doglist.app

import com.forbes.doglist.core.dataSource.remote.DogAppReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DogDetailsState(
    val progress: Boolean,
    val dogImages: List<String>
) : State

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
                    val dogImages = action.imageList
                    DogDetailsState(false, dogImages)
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
