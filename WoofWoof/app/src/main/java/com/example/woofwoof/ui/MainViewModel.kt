package com.example.woofwoof.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.woofwoof.data.repository.DogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()
    data class Success(val dogs: List<DogImage>) : UiState()
    data class Error(val message: String) : UiState()
}

class MainViewModel(private val repository: DogRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var currentBreed: String? = null

    init {
        fetchDogs()
    }

    fun fetchDogs(breed: String? = currentBreed) {
        currentBreed = breed
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val result = repository.getDogs(breed)
            if (result.isSuccess) {
                val dogImages = result.getOrNull()?.map { DogImage(imageUrl = it) } ?: emptyList()
                _uiState.value = UiState.Success(dogImages)
            } else {
                _uiState.value = UiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}
