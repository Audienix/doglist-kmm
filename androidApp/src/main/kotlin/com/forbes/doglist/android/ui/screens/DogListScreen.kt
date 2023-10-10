package com.forbes.doglist.android.ui.screens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import cafe.adriel.voyager.core.screen.Screen
import com.forbes.doglist.app.FeedAction
import com.forbes.doglist.app.FeedStore
import com.forbes.doglist.android.R
import com.forbes.doglist.android.ui.components.DogListItemCard
import com.forbes.doglist.android.ui.components.LoadingState
import com.forbes.doglist.android.ui.components.TopBar
import com.forbes.doglist.android.ui.theme.MaterialColorPalette
import com.forbes.doglist.android.ui.theme.SetStatusBarColor
import com.forbes.doglist.android.utils.getGridCellCount
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DogListScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {
        SetStatusBarColor(statusBarColor = MaterialColorPalette.surfaceContainerLow)

        val store: FeedStore by inject()

        LaunchedEffect(Unit) {
            store.dispatch(FeedAction.Refresh(true))
        }
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialColorPalette.surface),
            topBar = { TopBar() },
            content = { padding ->
                ShowDogListScreenContent(
                    padding = padding,
                    store = store
                ) {}
            },
            containerColor = MaterialColorPalette.surface, // somehow this doesn't work in dark mode
        )
    }
}

@Composable
fun ShowDogListScreenContent(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    store: FeedStore,
    onClick: (String) -> Unit
) {
    val state = store.observeState().collectAsState()
    val dogs = remember(state.value.feeds) { state.value.feeds }

    if (state.value.progress && state.value.feeds.isEmpty()) {
        LoadingState(modifier)
    } else {
        LazyVerticalGrid(
            modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp)),
            columns = GridCells.Fixed(getGridCellCount(configuration = LocalConfiguration.current)),
            contentPadding = padding,
            content = {
                itemsIndexed(dogs) { i, dog ->
                    DogListItemCard(dog) {}
                }
            })
    }
}
