package com.example.scavhunt.fragment.play

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scavhunt.PlayTasksActivity
import com.example.scavhunt.R
import com.example.scavhunt.ScavHuntApp
import com.example.scavhunt.db.ScavHunt
import com.example.scavhunt.db.ScavItem
import com.example.scavhunt.fragment.create.CreateHuntViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FragmentPlayHunt : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PlayScavHuntAdapter

    private val playHuntViewModel: PlayHuntViewModel by viewModels()
    private val createHuntViewModel: CreateHuntViewModel by activityViewModels()

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // Refresh list on return
        refresh()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play_hunt, container, false)

        // Set up RecyclerView
        recyclerView = view.findViewById<RecyclerView>(R.id.play_recycler_view)
        playHuntViewModel.items.value?.let {
            adapter = PlayScavHuntAdapter(it,
                { playHunt(it) },
                { deleteHunt(it) },
                { editHunt(it) })
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(view.context)
        }
        // Observe changes in data
        playHuntViewModel.items.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        refresh()

        return view
    }
    private fun playHunt(item: ScavHunt) {
        val intent = Intent(context, PlayTasksActivity::class.java).apply {
            putExtra("hunt", item)
        }
        startForResult.launch(intent)
    }
    private fun editHunt(item: ScavHunt) {
        val fragment = this
        GlobalScope.launch {
            createHuntViewModel.hunt = item
            createHuntViewModel.items = ScavHuntApp.scavItemDao.selectAllWith(item.id) as MutableList<ScavItem>
            GlobalScope.launch(Dispatchers.Main) {
                fragment.findNavController().navigate(R.id.create_hunt)
            }
        }
    }
    private fun deleteHunt(item: ScavHunt) {
        GlobalScope.launch {
            val scavItems = ScavHuntApp.scavItemDao.selectAllWith(item.id)
            ScavHuntApp.scavItemDao.delete(scavItems)
            ScavHuntApp.scavHuntDao.delete(item)
            refresh()
        }

    }
    private fun refresh() {
        GlobalScope.launch {
            playHuntViewModel.items.postValue(ScavHuntApp.scavHuntDao.selectAll())
        }
    }

}

class PlayHuntViewModel : ViewModel() {
    var items = MutableLiveData<List<ScavHunt>>()
    init {
        items.value = listOf()
    }
}