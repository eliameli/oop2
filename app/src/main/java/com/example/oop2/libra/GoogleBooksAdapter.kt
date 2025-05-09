package com.example.oop2.libra

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oop2.R
import com.example.oop2.models.Book

class GoogleBooksAdapter(
    private val items: List<Book>,
    private val onLongClick: (Book) -> Unit
) : RecyclerView.Adapter<GoogleBooksAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_title)
        val author: TextView = view.findViewById(R.id.item_author)

        init {
            view.setOnLongClickListener {
                val book = items[adapterPosition]
                onLongClick(book)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_google_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = items[position]
        holder.title.text = book.name
        holder.author.text = book.author
    }

    override fun getItemCount(): Int = items.size
}
