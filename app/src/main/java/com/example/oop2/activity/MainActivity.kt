package com.example.oop2.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = LibraryAdapter()
        recyclerView.adapter = adapter

        loadLibraryItems()
    }

    private fun loadLibraryItems() {
        val books = listOf(
            Book(1, true, "Маугли", "Джозеф Киплинг", 202),
            Book(11, true, "Звездные войны", "Джордж Лукас", 401)
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
        adapter.submitList(items)
    }
}