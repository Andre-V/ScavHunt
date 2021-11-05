package com.example.scavhunt.activity.playtasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scavhunt.R
import com.example.scavhunt.db.ScavItem

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
        private val defaultColor = v.background

        fun bind(item: ScavItem, position: Int) {
            if (item.completed) {
                v.setBackgroundResource(R.color.complete)
            }
            else {
                v.background = defaultColor
            }
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