package com.forbes.doglist.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.navigator.Navigator
import com.forbes.doglist.app.FeedSideEffect
import com.forbes.doglist.app.FeedStore
import com.forbes.doglist.android.ui.screens.DogListScreen
import com.forbes.doglist.android.ui.theme.AppBackground
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppBackground {
                val store: FeedStore by inject()
                val error = store.observeSideEffect()
                    .filterIsInstance<FeedSideEffect.Error>()
                    .collectAsState(null)
                LaunchedEffect(error.value) {
                    error.value?.let {
                        Toast.makeText(this@MainActivity, it.error.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                Navigator(DogListScreen())
            }
        }
    }
}
