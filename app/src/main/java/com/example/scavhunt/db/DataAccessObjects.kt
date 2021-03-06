package com.example.scavhunt.db

import androidx.room.*

@Dao
interface ScavHuntDao {
    // Returned rowid is equivalent to PK (according to https://www.sqlite.org/rowidtable.html)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(scavHunt: ScavHunt) : Long
    @Update()
    fun update(scavHunt: ScavHunt)
    @Query("SELECT * FROM scavHunt")
    fun selectAll() : List<ScavHunt>
    @Delete
    fun delete(scavHunt: ScavHunt)
}

@Dao
interface ScavItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(scavItem: ScavItem)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(scavItems: List<ScavItem>)
    @Update
    fun update(scavItem: ScavItem)
    @Query("SELECT * FROM scavItem WHERE scavHuntId = :id")
    fun selectAllWith(id: Int) : List<ScavItem>
    @Delete
    fun delete(scavItem: ScavItem)
    @Delete
    fun delete(scavItem: List<ScavItem>)
}

