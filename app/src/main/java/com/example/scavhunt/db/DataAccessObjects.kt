package com.example.scavhunt.db

import androidx.room.*

@Dao
interface ScavHuntDao {
    // Returned rowid is equivalent to PK (according to https://www.sqlite.org/rowidtable.html)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScavHunt(scavHunt: ScavHunt) : Long

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

