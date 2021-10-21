package com.example.scavhunt.db

import androidx.room.*

@Dao
interface ScavHuntDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScavHunt(scavHunt: ScavHunt)
    @Query("SELECT * FROM scavHunt")
    fun selectAllScavHunts() : List<ScavHunt>
}

@Dao
interface ScavItemDao {
    @Insert
    fun insertScavItem(scavItem: ScavItem)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScavItems(scavItems: List<ScavItem>)
}

