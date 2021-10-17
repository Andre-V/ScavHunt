package com.example.scavhunt.fragment.create

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scavhunt.R
import com.example.scavhunt.db.ScavItem
import java.util.*

class CreateScavHuntAdapter(
        private val data: List<ScavItem>,
        private val listener: (ScavItem) -> Unit
    ) : RecyclerView.Adapter<CreateScavHuntAdapter.ViewHolder>() {

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_create_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        private val title = v.findViewById<TextView>(R.id.row_create_title)
        private val desc = v.findViewById<TextView>(R.id.row_create_desc)

        fun bind(item: ScavItem) {
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