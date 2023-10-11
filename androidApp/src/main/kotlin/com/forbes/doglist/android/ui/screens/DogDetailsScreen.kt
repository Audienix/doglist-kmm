package com.forbes.doglist.android.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.forbes.doglist.android.R
import com.forbes.doglist.android.ui.components.AppIconButton
import com.forbes.doglist.android.ui.components.DogImageCarousel
import com.forbes.doglist.android.ui.components.LoadingState
import com.forbes.doglist.android.ui.components.Title
import com.forbes.doglist.android.ui.components.TopBar
import com.forbes.doglist.android.ui.theme.MaterialColorPalette
import com.forbes.doglist.android.ui.theme.SetStatusBarColor
import com.forbes.doglist.android.utils.NetworkUtils
import com.forbes.doglist.app.AppActions
import com.forbes.doglist.app.DogDetailsState
import com.forbes.doglist.app.DogDetailsStore
import com.forbes.doglist.core.models.DogBreed
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * A screen for displaying details of a specific dog breed.
 *
 * @param dogBreed The dog breed to display details for.
 * @author Arighna Maity
 */
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
                TopBar(title = dogBreed.name, navIcon = {
                    val navigator: Navigator = LocalNavigator.currentOrThrow
                    AppIconButton(
                        tint = MaterialColorPalette.onSurfaceVariant,
                        imageVector = Icons.Filled.ArrowBack
                    ) { navigator.pop() }
                })
            },
            content = { padding ->
                ShowDogDetailsScreenContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    breed = dogBreed,
                    state = state
                )
            },
            containerColor = MaterialColorPalette.surface
        )
    }

    @Composable
    private fun ShowDogDetailsScreenContent(
        modifier: Modifier = Modifier,
        breed: DogBreed,
        state: State<DogDetailsState>
    ) {
        if (!NetworkUtils.isConnected(LocalContext.current)) {
            Toast.makeText(
                LocalContext.current,
                stringResource(R.string.no_internet_connection),
                Toast.LENGTH_LONG
            ).show()
        } else {
            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(3f / 4f)
                    ) {
                        if (state.value.progress && state.value.dogImages.isEmpty()) {
                            LoadingState(
                                modifier = modifier,
                                loadingText = stringResource(id = R.string.progress_loader_text)
                            )
                        } else {
                            DogImageCarousel(value = state.value.dogImages, breed = breed)
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_16dp)))
                    Title(title = stringResource(id = R.string.title_sub_breed))
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
                }

                item {
                    var text = stringResource(id = R.string.no_sub_breeds)
                    if (breed.subBreeds.isNotEmpty()) {
                        text = breed.subBreeds
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
    }
}