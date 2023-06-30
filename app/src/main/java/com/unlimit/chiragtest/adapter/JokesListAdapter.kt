package com.unlimit.chiragtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.unlimit.chiragtest.R
import com.unlimit.chiragtest.model.JokesData
import kotlinx.android.synthetic.main.item_layout_jokes.view.*

class JokesListAdapter : ListAdapter<JokesData, JokesListAdapter.ItemViewHolder>(DiffCallback())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout_jokes, parent, false)
        )
    }

    override fun onBindViewHolder(holder:ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: JokesData) = with(itemView) {
         itemView.txt_joke.text= item.toString()
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<JokesData>() {
    override fun areItemsTheSame(oldItem: JokesData, newItem: JokesData): Boolean {
        return oldItem.joke == newItem.joke
    }

    override fun areContentsTheSame(oldItem: JokesData, newItem: JokesData): Boolean {
        return oldItem == newItem
    }
}