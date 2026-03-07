package com.example.xardcalamityfiles.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithAbilities(
    @Embedded val character: Character,
    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val abilities: List<Ability>
)
