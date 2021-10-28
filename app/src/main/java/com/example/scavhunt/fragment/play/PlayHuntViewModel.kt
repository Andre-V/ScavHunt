package com.example.scavhunt.fragment.play

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scavhunt.ScavHuntApp
import com.example.scavhunt.db.ScavHunt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayHuntViewModel : ViewModel() {
    var items = MutableLiveData<List<ScavHunt>>()
    init {
        items.value = listOf()
    }
    fun refresh() {
        // Reset value of items outside main thread
        viewModelScope.launch(Dispatchers.IO) {
            items.postValue(ScavHuntApp.scavHuntDao.selectAll())
        }
    }
    fun deleteHunt(item: ScavHunt) {
        // Delete selected ScavHunt item (Room uses ID)
        viewModelScope.launch(Dispatchers.IO) {
            val scavItems = ScavHuntApp.scavItemDao.selectAllWith(item.id)
            ScavHuntApp.scavItemDao.delete(scavItems)
            ScavHuntApp.scavHuntDao.delete(item)
            refresh()
        }
    }
}