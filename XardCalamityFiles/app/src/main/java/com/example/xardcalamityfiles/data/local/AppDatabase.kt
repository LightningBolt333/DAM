package com.example.xardcalamityfiles.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.xardcalamityfiles.data.model.Ability
import com.example.xardcalamityfiles.data.model.Character

@Database(entities = [Character::class, Ability::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "xard_calamity_files_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
