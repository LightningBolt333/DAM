package com.example.xardcalamityfiles.data.repository

import com.example.xardcalamityfiles.data.local.CharacterDao
import com.example.xardcalamityfiles.data.model.Ability
import com.example.xardcalamityfiles.data.model.Character
import com.example.xardcalamityfiles.data.model.CharacterWithAbilities
import kotlinx.coroutines.flow.Flow

class CharacterRepository(private val characterDao: CharacterDao) {

    val allCharacters: Flow<List<CharacterWithAbilities>> = characterDao.getAllCharacters()

    fun getCharacter(id: Long): Flow<CharacterWithAbilities?> {
        return characterDao.getCharacterWithAbilities(id)
    }

    suspend fun saveCharacterWithAbilities(character: Character, abilities: List<Ability>) {
        // Run in single transaction-like context by DAO or sequentially here
        if (character.id == 0L) {
            val insertedId = characterDao.insertCharacter(character)
            val abilitiesWithCharacterId = abilities.map { it.copy(characterId = insertedId) }
            characterDao.insertAbilities(abilitiesWithCharacterId)
        } else {
            characterDao.insertCharacter(character)
            // Delete old abilities and insert new ones
            characterDao.deleteAbilitiesByCharacterId(character.id)
            val abilitiesWithCharacterId = abilities.map { it.copy(characterId = character.id) }
            characterDao.insertAbilities(abilitiesWithCharacterId)
        }
    }

    suspend fun deleteCharacter(character: Character) {
        characterDao.deleteCharacter(character)
    }
}
