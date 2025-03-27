package com.example.oop2.libra
import com.example.oop2.*

import com.example.oop2.R


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.oop2.models.LibraryItem  // Убедись, что путь к модели правильный



class LibraryAdapter : ListAdapter<LibraryItem, LibraryAdapter.LibraryViewHolder>(DiffCallback()) {

    class LibraryViewHolder(itemView: View, private val adapter: LibraryAdapter) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: LibraryItem) {
            itemView.setOnClickListener {
                item.isAvailable = !item.isAvailable
                Toast.makeText(itemView.context, "Элемент с id ${item.id}", Toast.LENGTH_SHORT).show()
                adapter.notifyItemChanged(bindingAdapterPosition)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_library, parent, false)
        return LibraryViewHolder(view, this)
    }


    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<LibraryItem>() {
        override fun areItemsTheSame(oldItem: LibraryItem, newItem: LibraryItem) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: LibraryItem, newItem: LibraryItem) = oldItem == newItem
    }
}

