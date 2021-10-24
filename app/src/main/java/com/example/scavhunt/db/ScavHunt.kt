package com.example.scavhunt.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "scavHunt")
data class ScavHunt(
    var title: String,
    var rating: Int = 0,
    var completed: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable

@Parcelize
@Entity(tableName = "scavItem")
data class ScavItem(
    var title: String,
    var desc: String,
    var answer: String,
    var completed: Boolean = false,
    var scavHuntId: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable