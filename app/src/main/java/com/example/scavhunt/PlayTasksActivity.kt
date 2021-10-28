package com.example.scavhunt

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scavhunt.db.ScavHunt
import com.example.scavhunt.db.ScavItem
import com.example.scavhunt.fragment.PlayTasksViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayTasksActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PlayTasksAdapter
    var scavHunt: ScavHunt? = null

    private val playTaskViewModel: PlayTasksViewModel by viewModels()

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result -> when(result.resultCode) {
            RESULT_OK -> {
                result.data?.let {
                    intent ->
                    // Get returned ScavItem
                    val place = intent.getParcelableExtra<ScavItem>("item")
                    place?.let { scavItem ->
                        scavHunt?.let { scavHunt ->
                            // Update completion status
                            playTaskViewModel.updateHuntStatus(scavItem, scavHunt)
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_task)
        // Load intent
        // Apply intent data
        scavHunt = intent.getParcelableExtra<ScavHunt>("hunt")
        // Set up RecyclerView
        recyclerView = findViewById<RecyclerView>(R.id.play_recycler_view)
        playTaskViewModel.items.value?.let {
            adapter = PlayTasksAdapter(it) {
                answerTask(it)
            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        // Observe changes in data
        playTaskViewModel.items.observe(this, Observer {
            adapter.setData(it)
        })
        // Load ScavItems from DB corresponding to their ScavHunt
        scavHunt?.let {
            playTaskViewModel.refreshWithID(it.id)
        }
    }
    private fun answerTask(item : ScavItem) {
        val intent = Intent(this, AnswerTaskActivity::class.java).apply {
            putExtra("item", item)
        }
        startForResult.launch(intent)
    }
}



