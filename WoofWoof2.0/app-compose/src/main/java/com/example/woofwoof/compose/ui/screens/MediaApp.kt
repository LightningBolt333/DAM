package com.example.woofwoof.compose.ui.screens

import androidx.compose.animation.Crossfade
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.woofwoof.compose.ui.MainViewModel

@Composable
fun MediaApp(viewModel: MainViewModel) {
    val state by viewModel.uiState.collectAsState()

    BackHandler(enabled = state.selectedDog != null) {
        viewModel.selectItem(null)
    }

    Crossfade(targetState = state.selectedDog, label = "ScreenTransition") { selectedDog ->
        if (selectedDog != null) {
            DetailScreen(
                dog = selectedDog,
                onBack = { viewModel.selectItem(null) }
            )
        } else {
            ListScreen(
                state = state,
                onDogClick = { viewModel.selectItem(it) },
                onRetry = { viewModel.loadItems() },
                onSearch = { breed -> viewModel.loadItems(breed) },
                onRefresh = { viewModel.loadItems() }
            )
        }
    }
}
