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

    inner class LibraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.item_icon)
        private val name: TextView = itemView.findViewById(R.id.item_name)
        private val card: CardView = itemView.findViewById(R.id.item_card)

        fun bind(item: LibraryItem) {
            name.text = item.name
            icon.setImageResource(item.iconResId)

            // Изменение доступности элемента (прозрачность текста, тень карточки)
            name.alpha = if (item.isAvailable) 1.0f else 0.3f
            card.cardElevation = if (item.isAvailable) 10f else 1f

            itemView.setOnClickListener {
                item.isAvailable = !item.isAvailable
                Toast.makeText(itemView.context, "Элемент с id ${item.id}", Toast.LENGTH_SHORT).show()

                // Проверяем, что позиция валидная перед обновлением
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_library, parent, false)
        return LibraryViewHolder(view)
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
