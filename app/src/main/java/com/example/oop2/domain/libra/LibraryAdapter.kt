package com.example.oop2.domain.libra

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.oop2.R
import com.example.oop2.domain.model.LibraryItem

class LibraryAdapter(
    private val onItemClick: (LibraryItem) -> Unit
) : ListAdapter<LibraryItem, LibraryAdapter.LibraryViewHolder>(DiffCallback()) {

    class LibraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.item_icon)
        private val name: TextView = itemView.findViewById(R.id.item_name)
        private val idTextView: TextView = itemView.findViewById(R.id.item_id)
        private val card: CardView = itemView.findViewById(R.id.item_card)
        fun bind(item: LibraryItem) {
            if (item.iconResId != 0) {
                icon.setImageResource(item.iconResId)
            } else {
                icon.setImageResource(R.drawable.ic_default) // запасная картинка
            }

            name.text = item.name
            idTextView.text = itemView.context.getString(R.string.item_id, item.id)
            updateAvailabilityStyle(item.isAvailable)

            name.text = item.name
            name.isSelected = true

        }

        private fun updateAvailabilityStyle(isAvailable: Boolean) {
            if (isAvailable) {
                card.cardElevation = 10f
                name.alpha = 1f
                idTextView.alpha = 1f
            } else {
                card.cardElevation = 1f
                name.alpha = 0.3f
                idTextView.alpha = 0.3f
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_library, parent, false)
        return LibraryViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    fun removeItem(position: Int) {
        val currentList = currentList.toMutableList()
        currentList.removeAt(position)
        submitList(currentList)
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
