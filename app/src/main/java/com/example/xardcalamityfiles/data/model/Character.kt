package com.example.xardcalamityfiles.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val characterClass: String, // Attacker, Controller, Support, Defender
    val subclass: String, // Offensive, Disruptor, Catalyst, Protector
    val profilePictureUri: String? // URI string for the image
)
