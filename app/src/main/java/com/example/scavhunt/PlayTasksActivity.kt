package com.example.scavhunt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scavhunt.db.ScavHunt
import com.example.scavhunt.db.ScavItem
import com.example.scavhunt.fragment.play.PlayHuntViewModel
import com.example.scavhunt.fragment.play.PlayScavHuntAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayTasksActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PlayTasksAdapter

    private val playTaskData: PlayTaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_task)
        // Load intent
        // Apply intent data
        val scavHunt = intent.getParcelableExtra<ScavHunt>("hunt")
        // Set up RecyclerView
        recyclerView = findViewById<RecyclerView>(R.id.play_recycler_view)
        playTaskData.items.value?.let {
            adapter = PlayTasksAdapter(it) {

            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        // Observe changes in data
        playTaskData.items.observe(this, Observer {
            adapter.setData(it)
        })
        scavHunt?.let {
            GlobalScope.launch {
                playTaskData.items.postValue(
                    ScavHuntApp.scavItemDao.selectAllScavItemsWith(it.id)
                )
            }
        }

    }
}

class PlayTaskViewModel : ViewModel() {
    var items = MutableLiveData<List<ScavItem>>()
    init {
        items.value = listOf()
    }
}

class PlayTasksAdapter(
    private var data: List<ScavItem>,
    private val listener: (ScavItem) -> Unit
) : RecyclerView.Adapter<PlayTasksAdapter.ViewHolder>() {

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_play_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    fun setData(newData : List<ScavItem>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        private val title = v.findViewById<TextView>(R.id.row_play_task_title)
        private val desc = v.findViewById<TextView>(R.id.row_play_task_desc)

        fun bind(item: ScavItem, position: Int) {
            item.let {
                title.text = it.title
                desc.text = it.desc
            }
            v.setOnClickListener {
                listener(item)
            }
        }
    }
}