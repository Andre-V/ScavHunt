package com.example.scavhunt.fragment.create

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.scavhunt.ScavHuntApp
import com.example.scavhunt.db.ScavHunt
import com.example.scavhunt.db.ScavItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateHuntViewModel: ViewModel() {
    var hunt : ScavHunt = ScavHunt("")
    var items : MutableList<ScavItem> = mutableListOf()
    var itemsToDelete : MutableList<ScavItem> = mutableListOf()
    fun setItemsID(id : Int) {
        for (item in items) {
            item.scavHuntId = id
        }
    }
    fun editHunt(item: ScavHunt, fragment: Fragment, destination: Int) {
        // Set member values, retrieving ScavItems from DB.
        // Then, navigate to destination (Must be on Main thread)
        viewModelScope.launch(Dispatchers.IO) {
            hunt = item
            items = ScavHuntApp.scavItemDao.selectAllWith(item.id) as MutableList<ScavItem>
            itemsToDelete = mutableListOf()
            viewModelScope.launch(Dispatchers.Main) {
                fragment.findNavController().navigate(destination)
            }
        }
    }
    fun resetHunt() {
        // Resets member data to initial configuration
        hunt = ScavHunt("")
        items.clear()
        itemsToDelete.clear()
    }
    fun saveHunt() {
        // Saves viewModel data to DB. Uses ID (unmodified when user edits) to
        // determine whether to create new records or updating existing ones.
        viewModelScope.launch(Dispatchers.IO) {
            val rowid = ScavHuntApp.scavHuntDao.insert(hunt)
            setItemsID(rowid.toInt())
            ScavHuntApp.scavItemDao.insert(items)
            ScavHuntApp.scavItemDao.delete(itemsToDelete)
        }
    }
}