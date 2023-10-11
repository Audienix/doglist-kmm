package com.forbes.doglist.android.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.forbes.doglist.android.ui.components.AppIconButton
import com.forbes.doglist.android.ui.components.DogImage
import com.forbes.doglist.android.utils.getImageContentScale

/**
 * A screen that displays an image preview of a dog.
 *
 * @param imageUrl The URL of the dog image to be displayed.
 * @param dogBreed The breed of the dog for content description.
 * @author Arighna Maity
 */
class DogImagePreviewScreen(
    private val imageUrl: String,
    private val dogBreed: String
) : Screen {
    @Composable
    override fun Content() {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DogImage(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
                dogBreed = dogBreed,
                imageUrl = imageUrl,
                contentScale = getImageContentScale()
            )
            val navigator: Navigator = LocalNavigator.currentOrThrow
            AppIconButton(
                imageVector = Icons.Filled.Close,
                tint = Color.White
            ) { navigator.pop() }
        }
    }
}
