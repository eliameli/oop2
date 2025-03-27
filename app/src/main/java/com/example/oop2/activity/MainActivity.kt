package com.example.oop2  // Убедитесь, что пакет соответствует вашему проекту


import com.example.oop2.R
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop2.databinding.ActivityMainBinding
import com.example.oop2.libra.LibraryAdapter
import com.example.oop2.models.LibraryItem



class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = LibraryAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadLibraryItems()
    }

    private fun loadLibraryItems() {
        val items = listOf(
            LibraryItem(1, "Маугли", true, R.drawable.ic_book),
            LibraryItem(2, "Звездные войны", true, R.drawable.ic_book),
            LibraryItem(3, "Сельская жизнь", false, R.drawable.ic_newspaper),
            LibraryItem(4, "Дэдпул и Росомаха", true, R.drawable.ic_disk)
        )
        adapter.submitList(items)
    }
}
