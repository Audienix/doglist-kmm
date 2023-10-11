package com.forbes.doglist.android.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.forbes.doglist.android.R
import com.forbes.doglist.android.ui.components.DogListItemCard
import com.forbes.doglist.android.ui.components.LoadingState
import com.forbes.doglist.android.ui.components.TopBar
import com.forbes.doglist.android.ui.theme.MaterialColorPalette
import com.forbes.doglist.android.ui.theme.SetStatusBarColor
import com.forbes.doglist.android.utils.NetworkUtils
import com.forbes.doglist.android.utils.getGridCellCount
import com.forbes.doglist.app.AppActions
import com.forbes.doglist.app.DogListState
import com.forbes.doglist.app.DogListStore
import com.forbes.doglist.app.SideEffect
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * A screen for displaying a list of dog breeds.
 *
 * @author Arighna Maity
 */
class DogListScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {
        SetStatusBarColor(statusBarColor = MaterialColorPalette.surfaceContainerLow)
        val state = getDogListAPIState()
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialColorPalette.surface),
            topBar = {
                TopBar(title = stringResource(id = R.string.title_home))
            },
            content = { padding ->
                ShowDogListScreenContent(
                    padding = padding,
                    state = state
                )
            },
            containerColor = MaterialColorPalette.surface, // somehow this doesn't work in dark mode
        )
    }

    /**
     * Get the state of the DogList API call.
     *
     * @return The state of the DogList API call.
     * @author Arighna Maity
     */
    @Composable
    private fun getDogListAPIState(): State<DogListState> {
        val store: DogListStore by inject()
        val error = store.observeSideEffect()
            .filterIsInstance<SideEffect.Error>()
            .collectAsState(null)
        val context = LocalContext.current
        LaunchedEffect(error.value) {
            error.value?.let {
                Toast.makeText(context, it.error.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        val state = store.observeState().collectAsState()
        LaunchedEffect(Unit) {
            if (state.value.dogBreeds.isEmpty())
                store.dispatch(AppActions.FetchDogList)
        }
        return state
    }
}

@Composable
private fun ShowDogListScreenContent(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    state: State<DogListState>
) {
    if (!NetworkUtils.isConnected(LocalContext.current)) {
        Toast.makeText(
            LocalContext.current,
            stringResource(R.string.no_internet_connection),
            Toast.LENGTH_LONG
        ).show()
    } else {
        val dogs = remember(state.value.dogBreeds) { state.value.dogBreeds }

        if (state.value.progress && state.value.dogBreeds.isEmpty()) {
            LoadingState(
                modifier = modifier,
                loadingText = stringResource(id = R.string.progress_loader_text)
            )
        } else {
            val navigator = LocalNavigator.currentOrThrow
            LazyVerticalGrid(
                modifier = modifier.padding(dimensionResource(id = R.dimen.dimension_8dp)),
                columns = GridCells.Fixed(getGridCellCount()),
                contentPadding = padding,
                content = {
                    itemsIndexed(dogs) { _, dog ->
                        DogListItemCard(dog = dog, onItemClicked = {
                            navigator.push(item = DogDetailsScreen(dog))
                        })
                    }
                })
        }
    }
}
