package com.example.scavhunt.fragment.play

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scavhunt.R
import com.example.scavhunt.db.ScavHunt
import com.example.scavhunt.db.ScavItem
import com.example.scavhunt.fragment.create.CreateScavItemAdapter


class PlayScavHuntAdapter(
    private var data: List<ScavHunt>,
    private val listener: (ScavHunt) -> Unit
) : RecyclerView.Adapter<PlayScavHuntAdapter.ViewHolder>() {

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayScavHuntAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_play_hunt, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayScavHuntAdapter.ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    fun setData(newData : List<ScavHunt>) {
        data = newData
        notifyDataSetChanged()
    }
    
    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        private val title = v.findViewById<TextView>(R.id.row_play_title)
        private val rating = v.findViewById<TextView>(R.id.row_play_rating)
        private val completed = v.findViewById<TextView>(R.id.row_play_completed)
        private val defaultColor = v.background

        fun bind(item: ScavHunt, position: Int) {
            if (item.completed) {
                v.setBackgroundResource(R.color.complete)
            }
            else {
                v.background = defaultColor
            }
            item.let {
                title.text = it.title
                rating.text = it.rating.toString()
                completed.text = it.completed.toString()
            }
            v.setOnClickListener {
                listener(item)
            }
        }
    }
}