package com.example.scavhunt.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ScavHunt::class], version = 1)
abstract class ScavHuntDatabase : RoomDatabase() {
    abstract fun scavHuntDao() : ScavHuntDao
}

@Database(entities = [ScavItem::class], version = 1)
abstract class ScavItemDatabase : RoomDatabase() {
    abstract fun scavItemDao() : ScavItemDao
}