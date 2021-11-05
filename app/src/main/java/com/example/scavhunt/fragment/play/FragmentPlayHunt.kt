package com.example.scavhunt.fragment.play

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scavhunt.activity.playtasks.PlayTasksActivity
import com.example.scavhunt.R
import com.example.scavhunt.db.ScavHunt
import com.example.scavhunt.fragment.create.CreateHuntViewModel

class FragmentPlayHunt : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PlayScavHuntAdapter

    private val playHuntViewModel: PlayHuntViewModel by viewModels()
    private val createHuntViewModel: CreateHuntViewModel by activityViewModels()

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // Refresh list on return
        playHuntViewModel.refresh()
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
                { playHuntViewModel.deleteHunt(it) },
                { createHuntViewModel.editHunt(it, this, R.id.create_hunt) },
                { playHuntViewModel.updateHunt(it) })
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(view.context)
        }
        // Observe changes in data
        playHuntViewModel.items.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        playHuntViewModel.refresh()

        return view
    }
    private fun playHunt(item: ScavHunt) {
        val intent = Intent(context, PlayTasksActivity::class.java).apply {
            putExtra("hunt", item)
        }
        startForResult.launch(intent)
    }
}

