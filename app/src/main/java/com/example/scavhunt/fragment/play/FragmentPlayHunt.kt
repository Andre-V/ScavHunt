package com.example.scavhunt.fragment.play

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.scavhunt.R
import com.example.scavhunt.db.ScavHunt
import com.example.scavhunt.db.ScavHuntDatabase
import com.example.scavhunt.fragment.create.CreateScavItemAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FragmentPlayHunt : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PlayScavHuntAdapter

    private val playHuntData: PlayHuntViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play_hunt, container, false)

        // Create DB instance
        // TODO: Consider having to refresh to find new ones
        val dbHunt = Room.databaseBuilder(view.context, ScavHuntDatabase::class.java,
            "ScavHuntDatabase").build()

        // Set up RecyclerView
        recyclerView = view.findViewById<RecyclerView>(R.id.play_recycler_view)
        playHuntData.items.value?.let {
            adapter = PlayScavHuntAdapter(it)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(view.context)
        }
        playHuntData.items.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        GlobalScope.launch {
            val test = dbHunt.scavHuntDao().selectAllScavHunts()
            playHuntData.items.postValue(dbHunt.scavHuntDao().selectAllScavHunts())
        }



        return view
    }

}

class PlayHuntViewModel : ViewModel() {
    var items = MutableLiveData<List<ScavHunt>>()
    init {
        items.value = listOf()
    }
}