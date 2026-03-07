package com.example.xardcalamityfiles.data.local

import androidx.room.*
import com.example.xardcalamityfiles.data.model.Ability
import com.example.xardcalamityfiles.data.model.Character
import com.example.xardcalamityfiles.data.model.CharacterWithAbilities
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbility(ability: Ability)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbilities(abilities: List<Ability>)

    @Transaction
    @Query("SELECT * FROM characters ORDER BY name ASC")
    fun getAllCharacters(): Flow<List<CharacterWithAbilities>>

    @Transaction
    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    fun getCharacterWithAbilities(id: Long): Flow<CharacterWithAbilities?>

    @Delete
    suspend fun deleteCharacter(character: Character)
    
    @Query("DELETE FROM abilities WHERE characterId = :characterId")
    suspend fun deleteAbilitiesByCharacterId(characterId: Long)
}
