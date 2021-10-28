package com.example.scavhunt.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scavhunt.ScavHuntApp
import com.example.scavhunt.db.ScavHunt
import com.example.scavhunt.db.ScavItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayTasksViewModel : ViewModel() {
    var items = MutableLiveData<List<ScavItem>>()
    init {
        items.value = listOf()
    }
    fun refreshWithID(id : Int) {
        // Set value of items outside main thread all with the same ID.
        viewModelScope.launch(Dispatchers.IO) {
            items.postValue(ScavHuntApp.scavItemDao.selectAllWith(id))
        }
    }
    fun updateHuntStatus(scavItem: ScavItem, scavHunt: ScavHunt) {
        viewModelScope.launch(Dispatchers.IO) {
            // Update item
            ScavHuntApp.scavItemDao.update(scavItem)
            // Load items
            val updatedItems = ScavHuntApp.scavItemDao.selectAllWith(scavHunt.id)
            // Reload items
            items.postValue(updatedItems)
            // Update ScavHunt completion status
            var allComplete = true
            for (item in updatedItems) {
                if (!item.completed) {
                    allComplete = false
                    break
                }
            }
            scavHunt.completed = allComplete
            ScavHuntApp.scavHuntDao.update(scavHunt)
        }

    }
}