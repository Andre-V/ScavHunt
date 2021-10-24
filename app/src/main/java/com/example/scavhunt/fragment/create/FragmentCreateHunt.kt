package com.example.scavhunt.fragment.create

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.scavhunt.CreateScavItemActivity
import com.example.scavhunt.R
import com.example.scavhunt.ScavHuntApp
import com.example.scavhunt.db.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*

class FragmentCreateHunt : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: CreateScavItemAdapter
    //TODO: change scope to entire activity to edit previously made scavenger hunts
    private val createHuntData: CreateHuntViewModel by viewModels()

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result -> when(result.resultCode) {
            RESULT_OK -> {
                result.data?.let { intent ->
                    // Get returned data
                    val place = intent.getParcelableExtra<ScavItem>("item")
                    val index = intent.getIntExtra("index", -1)
                    place?.let {
                        // Set ID to reference parent hunt
                        //createHuntData.hunt.id
                        // Determine to either overwrite existing data or append
                        if (index < 0) {
                            createHuntData.items.add(it)
                            recyclerView.adapter?.notifyItemInserted(createHuntData.items.size - 1)
                        }
                        else {
                            createHuntData.items[index] = it
                            recyclerView.adapter?.notifyItemChanged(index)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_hunt, container, false)

        // Set up RecyclerView
        recyclerView = view.findViewById<RecyclerView>(R.id.create_recycler_view)
        adapter = CreateScavItemAdapter(createHuntData.items) {
            // Set launch intent event for each item
            item: ScavItem, int : Int -> launchItemActivity(item, int, view.context)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        // Set launch intent events for FAB
        view.findViewById<FloatingActionButton>(R.id.create_floating_button).setOnClickListener {
            launchItemActivity(null, null, view.context)
        }

        // Change ViewModel on text change
        view.findViewById<TextInputLayout>(R.id.create_text_input_layout).apply {
            editText?.doAfterTextChanged {
                it?.let {
                    createHuntData.hunt.title = it.toString()
                }
            }
        }

        // Set save event for submit button
        view.findViewById<Button>(R.id.create_submit).setOnClickListener {
            saveScavHunt()
        }

        // Load any data from ViewModel
        return view
    }
    private fun launchItemActivity(item: ScavItem?, index: Int?, context: Context) {
        val intent = Intent(context, CreateScavItemActivity::class.java).apply {
            putExtra("item", item)
            putExtra("index", index)
        }
        startForResult.launch(intent)
    }
    private fun saveScavHunt() {
        GlobalScope.launch(Dispatchers.IO) {
            val rowid = ScavHuntApp.scavHuntDao.insertScavHunt(createHuntData.hunt)
            createHuntData.setItemsID(rowid.toInt())
            ScavHuntApp.scavItemDao.insertScavItems(createHuntData.items)
        }
    }

}

class CreateHuntViewModel: ViewModel() {
    var hunt : ScavHunt = ScavHunt("")
    var items : MutableList<ScavItem> = mutableListOf()
    fun setItemsID(id : Int) {
        for (item in items) {
            item.scavHuntId = id
        }
    }
}