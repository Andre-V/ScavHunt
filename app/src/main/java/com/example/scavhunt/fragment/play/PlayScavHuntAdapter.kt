package com.example.scavhunt.fragment.play

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scavhunt.R
import com.example.scavhunt.db.ScavHunt


class PlayScavHuntAdapter(
    private var data: List<ScavHunt>,
    private val playListener: (ScavHunt) -> Unit,
    private val deleteListener: (ScavHunt) -> Unit,
    private val editListener: (ScavHunt) -> Unit,
    private val ratingListener: (ScavHunt) -> Unit
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
        private val rating = v.findViewById<RatingBar>(R.id.row_play_rating)
        private val playButton = v.findViewById<ImageButton>(R.id.row_play_play)
        private val editButton = v.findViewById<ImageButton>(R.id.row_play_edit)
        private val deleteButton = v.findViewById<ImageButton>(R.id.row_play_delete)

        fun bind(item: ScavHunt, position: Int) {
            if (item.completed) {
                v.setBackgroundResource(R.drawable.row_shape_complete)
            }
            else {
                v.setBackgroundResource(R.drawable.row_shape)
            }
            item.let {
                title.text = it.title
                rating.rating = it.rating.toFloat()
            }
            playButton.setOnClickListener {
                playListener(item)
            }
            editButton.setOnClickListener {
                editListener(item)
            }
            deleteButton.setOnClickListener {
                deleteListener(item)
            }
            rating.setOnRatingBarChangeListener { ratingBar: RatingBar, fl: Float, b: Boolean ->
                item.rating = fl.toInt()
                ratingListener(item)
            }
        }
    }
}