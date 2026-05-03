package com.example.woofwoof.compose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.woofwoof.core.repository.DogRepository
import com.example.woofwoof.core.model.DogImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val dogs: List<DogImage> = emptyList(),
    val selectedDog: DogImage? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class MainViewModel(private val repository: DogRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var currentBreed: String? = null

    init {
        loadItems()
    }

    fun loadItems(breed: String? = currentBreed) {
        currentBreed = breed
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            val result = repository.getDogs(breed)
            if (result.isSuccess) {
                val dogImages = result.getOrNull() ?: emptyList()
                _uiState.update { it.copy(isLoading = false, dogs = dogImages) }
            } else {
                _uiState.update { it.copy(isLoading = false, errorMessage = result.exceptionOrNull()?.message ?: "Unknown error") }
            }
        }
    }

    fun selectItem(dog: DogImage?) {
        _uiState.update { it.copy(selectedDog = dog) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
