package com.example.scavhunt

import android.app.Application
import com.example.scavhunt.db.ScavHuntDatabase

// Complete the database singleton pattern by extending Application class
class ScavHuntApp() : Application() {
    override fun onCreate() {
        super.onCreate()
        database = ScavHuntDatabase.getDatabase(applicationContext)
    }
    companion object {
        lateinit var database : ScavHuntDatabase
        val scavHuntDao by lazy { database.scavHuntDao() }
        val scavItemDao by lazy { database.scavItemDao() }
    }
}