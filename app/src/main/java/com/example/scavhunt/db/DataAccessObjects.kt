package com.example.scavhunt.db

import androidx.room.*

@Dao
interface ScavHuntDao {
    @Insert
    fun insertScavHunt(scavHunt: ScavHunt)
    @Query("SELECT * FROM ScavHunt")
    fun selectAllScavHunts() : List<ScavHunt>
}

@Dao
interface ScavItemDao {
    @Insert
    fun insertScavItem(scavItem: ScavItem)
}

