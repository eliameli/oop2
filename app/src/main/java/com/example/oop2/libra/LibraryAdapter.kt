package com.example.oop2.libra

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
import com.example.oop2.R
import com.example.oop2.models.LibraryItem

class LibraryAdapter : ListAdapter<LibraryItem, LibraryAdapter.LibraryViewHolder>(DiffCallback()) {

    class LibraryViewHolder(itemView: View, private val adapter: LibraryAdapter) :
        RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.item_name)
        private val iconImageView: ImageView = itemView.findViewById(R.id.item_icon)
        private val cardView: CardView = itemView.findViewById(R.id.item_card)

        fun bind(item: LibraryItem) {
            nameTextView.text = item.name
            iconImageView.setImageResource(item.iconResId)

            if (item.isAvailable) {
                cardView.elevation = 10f
                nameTextView.alpha = 1.0f
            } else {
                cardView.elevation = 1f
                nameTextView.alpha = 0.3f
            }

            itemView.setOnClickListener {
                item.isAvailable = !item.isAvailable
                Toast.makeText(itemView.context, "Элемент с id ${item.id}", Toast.LENGTH_SHORT).show()
                adapter.notifyItemChanged(adapterPosition)
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
        override fun areContentsTheSame(oldItem: LibraryItem, newItem: LibraryItem): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.isAvailable == newItem.isAvailable
        }
    }
}
