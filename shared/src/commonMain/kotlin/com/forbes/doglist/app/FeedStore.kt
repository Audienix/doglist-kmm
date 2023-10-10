package com.forbes.doglist.app

import com.forbes.doglist.core.dataSource.remote.ApiResult
import com.forbes.doglist.core.dataSource.remote.DogFeedReader
import com.forbes.doglist.core.models.DogBreed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class FeedState(
    val progress: Boolean,
    val feeds: List<DogBreed>
) : State


sealed class FeedAction : Action {
    data class Refresh(val forceLoad: Boolean) : FeedAction()
    data class Data(val feeds: ApiResult<List<DogBreed>>) : FeedAction()
    data class Error(val error: Exception) : FeedAction()
}

sealed class FeedSideEffect : Effect {
    data class Error(val error: Exception) : FeedSideEffect()
}

class FeedStore(
    private val dogFeedReader: DogFeedReader
) : Store<FeedState, FeedAction, FeedSideEffect>,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val state = MutableStateFlow(FeedState(false, emptyList()))
    private val sideEffect = MutableSharedFlow<FeedSideEffect>()

    override fun observeState(): StateFlow<FeedState> = state

    override fun observeSideEffect(): Flow<FeedSideEffect> = sideEffect

    override fun dispatch(action: FeedAction) {
        val oldState = state.value

        val newState = when (action) {
            is FeedAction.Refresh -> {
                if (oldState.progress) {
                    launch { sideEffect.emit(FeedSideEffect.Error(Exception("In progress"))) }
                    oldState
                } else {
                    launch { loadAllFeeds(action.forceLoad) }
                    oldState.copy(progress = true)
                }
            }
            is FeedAction.Data -> {
                if (oldState.progress) {
                    val feeds = action.feeds.data
                    feeds?.let {dogs -> FeedState(false, dogs) }
                } else {
                    launch { sideEffect.emit(FeedSideEffect.Error(Exception("Unexpected action"))) }
                    oldState
                }
            }

            is FeedAction.Error -> {
                if (oldState.progress) {
                    launch { sideEffect.emit(FeedSideEffect.Error(action.error)) }
                    FeedState(false, oldState.feeds)
                } else {
                    launch { sideEffect.emit(FeedSideEffect.Error(Exception("Unexpected action"))) }
                    oldState
                }
            }
        }

        if (newState != oldState) {
            if (newState != null) {
                state.value = newState as FeedState
            }
        }
    }

    private suspend fun loadAllFeeds(forceLoad: Boolean) {
        try {
            val allFeeds = dogFeedReader.getDogList(forceLoad)
            dispatch(FeedAction.Data(allFeeds))
        } catch (e: Exception) {
            dispatch(FeedAction.Error(e))
        }
    }
}
