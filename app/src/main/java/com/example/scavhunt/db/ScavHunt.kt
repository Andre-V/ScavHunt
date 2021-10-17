package com.example.scavhunt.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScavHunt(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val rating: Int,
    val completed: Boolean
)

@Entity
data class ScavItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val scavHuntId: Int,
    val title: String,
    val desc: String,
    val answer: String,
    val completed: Boolean
)