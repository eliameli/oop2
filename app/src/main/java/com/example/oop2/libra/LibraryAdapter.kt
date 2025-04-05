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
import com.example.oop2.models.*

class LibraryAdapter : ListAdapter<LibraryItem, LibraryAdapter.LibraryViewHolder>(DiffCallback()) {

    class LibraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.item_name)
        private val iconImageView: ImageView = itemView.findViewById(R.id.item_icon)
        private val cardView: CardView = itemView.findViewById(R.id.item_card)
        private val idTextView: TextView = itemView.findViewById(R.id.item_id)



        fun bind(item: LibraryItem, adapter: LibraryAdapter) {
            itemView.findViewById<TextView>(R.id.item_name).text = item.name
            nameTextView.text = item.name
            iconImageView.setImageResource(item.getIconResId())
            idTextView.text = itemView.context.getString(R.string.item_id, item.id)



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
        return LibraryViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(getItem(position), this)
    }

    fun removeItem(position: Int) {
        val newList = currentList.toMutableList()
        newList.removeAt(position)
        submitList(newList)
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
