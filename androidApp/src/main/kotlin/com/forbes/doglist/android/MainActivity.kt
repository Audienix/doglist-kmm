package com.forbes.doglist.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import com.forbes.doglist.android.ui.screens.DogListScreen
import com.forbes.doglist.android.ui.theme.AppBackground

/**
 * The main activity of the Dog List app.
 *
 * @author Arighna Maity
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppBackground {
                Navigator(DogListScreen())
            }
        }
    }
}
