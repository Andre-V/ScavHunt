package com.example.scavhunt.fragment.create

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateScavItem(
    var title: String,
    var desc: String,
    var answer: String
) : Parcelable