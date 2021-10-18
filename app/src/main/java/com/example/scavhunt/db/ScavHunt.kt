package com.example.scavhunt.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class ScavHunt(
    val title: String,
    val rating: Int = 0,
    val completed: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable

@Parcelize
@Entity
data class ScavItem(
    val title: String,
    val desc: String,
    val answer: String,
    val completed: Boolean = false,
    val scavHuntId: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable