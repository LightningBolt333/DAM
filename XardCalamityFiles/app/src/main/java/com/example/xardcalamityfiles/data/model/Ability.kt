package com.example.xardcalamityfiles.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "abilities",
    foreignKeys = [
        ForeignKey(
            entity = Character::class,
            parentColumns = ["id"],
            childColumns = ["characterId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("characterId")]
)
data class Ability(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val characterId: Long,
    val type: String, // "BASIC", "ABILITY_1", "ABILITY_2", "ABILITY_3", "SUPREME", "PASSIVE", "EFFECT"
    val name: String,
    val description: String,
    val iconUri: String? // Optional Icon URI
)
