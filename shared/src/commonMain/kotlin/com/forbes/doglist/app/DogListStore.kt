package com.forbes.doglist.app

import com.forbes.doglist.core.dataSource.remote.DogAppReader
import com.forbes.doglist.core.models.DogBreed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DogListState(
    val progress: Boolean,
    val dogBreeds: List<DogBreed>
) : State

sealed class SideEffect : Effect {
    data class Error(val error: Exception) : SideEffect()
}

class DogListStore(
    private val dogAppReader: DogAppReader
) : Store<DogListState, AppActions, SideEffect>,
    CoroutineScope by CoroutineScope(Dispatchers.IO) {

    private val state = MutableStateFlow(DogListState(false, emptyList()))
    private val sideEffect = MutableSharedFlow<SideEffect>()
    override fun observeState(): StateFlow<DogListState> = state

    override fun observeSideEffect(): Flow<SideEffect> = sideEffect

    override fun dispatch(action: AppActions) {
        val oldState = state.value

        val newState = when (action) {
            is AppActions.FetchDogList -> {
                if (oldState.progress) {
                    launch { sideEffect.emit(SideEffect.Error(Exception("In progress"))) }
                    oldState
                } else {
                    launch { fetchAllDogBreeds() }
                    oldState.copy(progress = true)
                }
            }

            is AppActions.DisplayDogList -> {
                if (oldState.progress) {
                    val list = action.listApiResult.data
                    list?.let { dogs -> DogListState(false, dogs) }
                } else {
                    launch { sideEffect.emit(SideEffect.Error(Exception("Unexpected action"))) }
                    oldState
                }
            }

            is AppActions.Error -> {
                if (oldState.progress) {
                    launch { sideEffect.emit(SideEffect.Error(action.error)) }
                    DogListState(false, oldState.dogBreeds)
                } else {
                    launch { sideEffect.emit(SideEffect.Error(Exception("Unexpected action"))) }
                    oldState
                }
            }

            else -> {oldState}
        }

        if (newState != oldState) {
            if (newState != null) {
                state.value = newState
            }
        }
    }

    private suspend fun fetchAllDogBreeds() {
        try {
            val allFeeds = dogAppReader.getDogList()
            dispatch(AppActions.DisplayDogList(allFeeds))
        } catch (e: Exception) {
            dispatch(AppActions.Error(e))
        }
    }
}
