package com.example.oop2.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop2.R
import com.example.oop2.libra.LibraryAdapter
import com.example.oop2.models.*


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LibraryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = LibraryAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadLibraryItems()


        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                adapter.removeItem(position)
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun loadLibraryItems() {
        val books = listOf(

            Book(1, true, "Маугли", "Джозеф Киплинг", 202),
            Book(11, true, "Звездные войны, Часть 1", "Джордж Лукас", 401),
            Book(12, true, "Звездные войны, Часть 2", "Джордж Лукас", 412),
            Book(13, true, "Звездные войны, Часть 3", "Джордж Лукас", 441),
            Book(14, true, "Звездные войны, Часть 4", "Джордж Лукас", 423),
            Book(15, true, "Звездные войны, Часть 5", "Джордж Лукас", 363),
            Book(16, true, "Звездные войны, Часть 6", "Джордж Лукас", 621),


        )

        val disks = listOf(
            Disk(3, true, "Дэдпул и Росомаха", DiskType.CD),
            Disk(31, true, "Один Дома", DiskType.DVD)
        )

        val newspapers = listOf(
            Newspaper(2, true, "Сельская жизнь", 794, 3),
            Newspaper(21, true, "Семья", 23, 12)
        )


        val items = books + disks + newspapers
        adapter.submitList(items.toList())

    }
}