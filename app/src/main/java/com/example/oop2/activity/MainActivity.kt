package com.example.oop2.activity



import com.example.oop2.R
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop2.databinding.ActivityMainBinding
import com.example.oop2.libra.LibraryAdapter
import com.example.oop2.models.*
import com.example.oop2.*



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
    private val items = mutableListOf<LibraryItem>()

    private fun loadLibraryItems() {


        val books = listOf(
            Book(1, true, "Маугли", "Джозеф Киплинг", 202),
            Book(11, true, "Звездные войны", "Джордж Лукас", 401)
        )

        val disks = listOf(

            Disk(3, true, "Дэдпул и Росомаха", DiskType.CD),
            Disk(31, true, "Один Дома", DiskType.DVD)
        )
        val newspaper = listOf(
            Newspaper(2, true, "Сельская жизнь",  794, 3),
            Newspaper(21, true, "Семья",   23, 12)
        )




        adapter.submitList(items)
    }
}
