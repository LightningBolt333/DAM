package com.example.xardcalamityfiles.content.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xardcalamityfiles.data.model.CharacterWithAbilities
import com.example.xardcalamityfiles.data.repository.CharacterRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ListViewModel(private val repository: CharacterRepository) : ViewModel() {

    // Converts Flow to StateFlow, providing a hot stream of all characters
    val allCharacters: StateFlow<List<CharacterWithAbilities>> = repository.allCharacters
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
