package com.example.xardcalamityfiles.content.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xardcalamityfiles.data.model.Ability
import com.example.xardcalamityfiles.data.model.Character
import com.example.xardcalamityfiles.data.model.CharacterWithAbilities
import com.example.xardcalamityfiles.data.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

sealed class CreateUiState {
    object Idle : CreateUiState()
    object Loading : CreateUiState()
    data class Success(val characterWithAbilities: CharacterWithAbilities) : CreateUiState()
    object Error : CreateUiState()
}

class CreateViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<CreateUiState>(CreateUiState.Idle)
    val uiState: StateFlow<CreateUiState> = _uiState.asStateFlow()

    fun loadCharacter(id: Long) {
        if (id == -1L) return
        viewModelScope.launch {
            _uiState.value = CreateUiState.Loading
            val charData = repository.getCharacter(id).firstOrNull()
            if (charData != null) {
                _uiState.value = CreateUiState.Success(charData)
            } else {
                _uiState.value = CreateUiState.Error
            }
        }
    }

    fun saveCharacter(character: Character, abilities: List<Ability>, onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.saveCharacterWithAbilities(character, abilities)
            onComplete()
        }
    }
}
