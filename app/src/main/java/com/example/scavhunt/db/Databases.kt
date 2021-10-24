package com.example.scavhunt.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ScavHunt::class, ScavItem::class], version = 1)
abstract class ScavHuntDatabase : RoomDatabase() {
    abstract fun scavHuntDao() : ScavHuntDao
    abstract fun scavItemDao() : ScavItemDao
    companion object {
        // Use singleton pattern as per Android recommendations to save performance
        // and since only one instance is needed. Also ensures same database file is accessed.
        // @Volatile and synchronized is use to account for multithreading scenarios.
        @Volatile
        private var INSTANCE: ScavHuntDatabase? = null
        fun getDatabase(context: Context) : ScavHuntDatabase {
            return INSTANCE ?:  synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScavHuntDatabase::class.java,
                    "ScavHuntDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

// Complete the singleton pattern by extending Application class
// which can be
class ScavHuntApp : Application() {
    val database by lazy { ScavHuntDatabase.getDatabase(this) }
    val scavHuntDao by lazy { database.scavHuntDao() }
    val scavItemDao by lazy { database.scavItemDao() }
}

/*@Database(entities = [ScavItem::class], version = 1)
abstract class ScavItemDatabase : RoomDatabase() {
    abstract fun scavItemDao() : ScavItemDao
}*/