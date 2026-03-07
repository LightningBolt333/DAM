package com.example.xardcalamityfiles.content.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xardcalamityfiles.data.model.CharacterWithAbilities
import com.example.xardcalamityfiles.data.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

sealed class DetailsUiState {
    object Loading : DetailsUiState()
    data class Success(val characterWithAbilities: CharacterWithAbilities) : DetailsUiState()
    object NotFound : DetailsUiState()
}

class DetailsViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun loadCharacter(id: Long) {
        viewModelScope.launch {
            repository.getCharacter(id).collectLatest { character ->
                if (character != null) {
                    _uiState.value = DetailsUiState.Success(character)
                } else {
                    _uiState.value = DetailsUiState.NotFound
                }
            }
        }
    }
}
