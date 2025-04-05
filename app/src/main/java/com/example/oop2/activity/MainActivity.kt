package com.example.oop2.activity

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import com.example.oop2.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.oop2.libra.LibraryAdapter
import com.example.oop2.models.*
import android.widget.Button

//import androidx.recyclerview.widget.DiffUtil


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var libraryAdapter: LibraryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Библиотека"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.purple_200)))

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = LibraryAdapter()
        recyclerView.adapter = adapter

        recyclerView.adapter = libraryAdapter

        val items = loadLibraryItems()
        libraryAdapter.submitList(items)

        setupSwipeToDelete()


        val addButton = findViewById<Button>(R.id.add_button)
        addButton.setOnClickListener {
            openNewItemScreen()
        }
    }
    private fun loadLibraryItems(): List<LibraryItem> {
        val books = listOf(
            Book(1, true, "Маугли", "Джозеф Киплинг", 202),
            Book(11, true, "Звездные войны, Часть 1", "Джордж Лукас", 401),
            Book(12, true, "Звездные войны, Часть 2", "Джордж Лукас", 412),
            Book(13, true, "Звездные войны, Часть 3", "Джордж Лукас", 441),
            Book(14, true, "Звездные войны, Часть 4", "Джордж Лукас", 423),
            Book(15, true, "Звездные войны, Часть 5", "Джордж Лукас", 363),
            Book(16, true, "Звездные войны, Часть 6", "Джордж Лукас", 621)
        )

        val disks = listOf(
            Disk(3, true, "Дэдпул и Росомаха", DiskType.CD),
            Disk(31, true, "Один Дома", DiskType.DVD)
        )

        val newspapers = listOf(
            Newspaper(2, true, "Сельская жизнь", 794, 3),
            Newspaper(21, true, "Семья", 23, 12)
        )

        return books + disks + newspapers
    }


    private fun setupSwipeToDelete() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                libraryAdapter.removeItem(viewHolder.adapterPosition)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

   // class DiffCallback : DiffUtil.ItemCallback<LibraryItem>() {
   //     override fun areItemsTheSame(oldItem: LibraryItem, newItem: LibraryItem) = oldItem.id == newItem.id
   //     override fun areContentsTheSame(oldItem: LibraryItem, newItem: LibraryItem) = oldItem == newItem
   // }




    // Теперь эта функция будет вызываться при нажатии на кнопку
    private fun openNewItemScreen() {
        val intent = Intent(this, ItemDetailsActivity::class.java)
        intent.putExtra("isNewItem", true)
        startActivity(intent)
    }
}