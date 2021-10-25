package com.example.scavhunt.fragment.create

import androidx.lifecycle.ViewModel
import com.example.scavhunt.db.ScavHunt
import com.example.scavhunt.db.ScavItem

class CreateHuntViewModel: ViewModel() {
    var hunt : ScavHunt = ScavHunt("")
    var items : MutableList<ScavItem> = mutableListOf()
    var itemsToDelete : MutableList<ScavItem> = mutableListOf()
    fun setItemsID(id : Int) {
        for (item in items) {
            item.scavHuntId = id
        }
    }
}