package com.forbes.doglist.android.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.imageLoader
import com.forbes.doglist.android.R
import com.forbes.doglist.android.ui.components.TopBar
import com.forbes.doglist.android.ui.theme.MaterialColorPalette
import com.forbes.doglist.android.ui.theme.SetStatusBarColor
import com.forbes.doglist.app.DogDetailsState
import com.forbes.doglist.app.DogDetailsStore
import com.forbes.doglist.app.AppActions
import com.forbes.doglist.core.models.DogBreed
import com.google.accompanist.pager.HorizontalPagerIndicator
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DogDetailsScreen(private val dogBreed: DogBreed) : Screen, KoinComponent {
    @Composable
    override fun Content() {
        SetStatusBarColor(statusBarColor = MaterialColorPalette.surfaceContainerLow)

        val store: DogDetailsStore by inject()
        val state = store.observeState().collectAsState()
        LaunchedEffect(Unit) {
            store.dispatch(AppActions.FetchDogImages(dogBreed.name))
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialColorPalette.surface),
            topBar = {
                val navigator: Navigator = LocalNavigator.currentOrThrow
                TopBar(title = dogBreed.name, navIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            null,
                            tint = MaterialColorPalette.onSurfaceVariant
                        )
                    }
                })
            },
            content = { padding ->
                ShowDogDetailsScreenContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    dogbreed = dogBreed,
                    state = state
                )
            },
            containerColor = MaterialColorPalette.surface
        )
    }

    @Composable
    private fun ShowDogDetailsScreenContent(
        modifier: Modifier = Modifier,
        dogbreed: DogBreed,
        state: State<DogDetailsState>
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            item {
                DogImageCarousel(state.value.dogImages)
            }
            item {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_16dp)))
                Title(title = stringResource(id = R.string.title_sub_breed))
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
            }

            item {
                var text = stringResource(id = R.string.no_sub_breeds)
                if (dogbreed.subBreeds.isNotEmpty()) {
                    text = dogbreed.subBreeds
                }
                Text(
                    text = text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 0.dp, 0.dp),
                    color = MaterialColorPalette.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start
                )
            }
        }

    }

    @Composable
    @OptIn(ExperimentalFoundationApi::class)
    private fun DogImageCarousel(value: List<String>) {
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { value.size }
        )
        HorizontalPager(
            state = pagerState
        ) { page ->
            AsyncImage(
                model = value[page],
                contentDescription = "",
                imageLoader = LocalContext.current.imageLoader,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                filterQuality = FilterQuality.High
            )
        }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimension_8dp)))
        Box(modifier = Modifier.fillMaxWidth()) {
            HorizontalPagerIndicator(
                modifier = Modifier.align(Alignment.Center),
                pageCount = value.size,
                pagerState = pagerState,
            )
        }
    }

    @Composable
    fun Title(title: String) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 0.dp, 0.dp),
            color = MaterialColorPalette.primary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Start
        )
    }
}