package com.example.xardcalamityfiles

import android.app.Application
import com.example.xardcalamityfiles.data.local.AppDatabase
import com.example.xardcalamityfiles.data.repository.CharacterRepository

class App : Application() {
    
    // Lazy evaluation for the database and repository so they are only initialized when needed
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { CharacterRepository(database.characterDao()) }

    override fun onCreate() {
        super.onCreate()
    }
}
