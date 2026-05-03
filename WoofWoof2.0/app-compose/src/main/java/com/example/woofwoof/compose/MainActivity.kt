package com.example.woofwoof.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.woofwoof.compose.ui.MainViewModel
import com.example.woofwoof.compose.ui.MainViewModelFactory
import com.example.woofwoof.compose.ui.screens.MediaApp
import com.example.woofwoof.core.network.ApiClient
import com.example.woofwoof.core.repository.DogRepository

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(DogRepository(ApiClient.dogApiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MediaApp(viewModel = viewModel)
                }
            }
        }
    }
}
