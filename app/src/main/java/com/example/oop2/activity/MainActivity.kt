package com.example.oop2.activity

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.oop2.R
import com.example.oop2.libra.LibraryAdapter
import com.example.oop2.libra.LibraryViewModel
import com.example.oop2.models.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var libraryAdapter: LibraryAdapter
    private lateinit var viewModel: LibraryViewModel
    private lateinit var addButton: FloatingActionButton
    private val addItemLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            viewModel.refresh()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Библиотека"
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(this, R.color.purple_200))
        )
        // start UI
        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.add_button)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // viewModel
        viewModel = ViewModelProvider(this)[LibraryViewModel::class.java]
        // aдаптер
        libraryAdapter = LibraryAdapter { item -> openItemDetails(item) }
        recyclerView.adapter = libraryAdapter

        viewModel.items.observe(this) { itemList ->
            libraryAdapter.submitList(itemList.toList())
        }
        // прелоад
        viewModel.preloadItems()
        // добавление
        addButton.setOnClickListener {
            val intent = Intent(this, ItemDetailsActivity::class.java)
            intent.putExtra("isNewItem", true)
            addItemLauncher.launch(intent)
        }
        setupSwipeToDelete()
    }
    private fun openItemDetails(item: LibraryItem) {
        val intent = Intent(this, ItemDetailsActivity::class.java).apply {
            putExtra("item_type", item.type)
            putExtra("item_icon", item.iconResId)
            putExtra("item_name", item.name)
            putExtra("item_id", item.id)

            when (item) {
                is Book -> {
                    putExtra("book_author", item.author)
                    putExtra("book_number_of_pages", item.pages)
                }
                is Disk -> putExtra("disk_type", item.diskType.name)
                is Newspaper -> {
                    putExtra("newspaper_month", item.month)
                    putExtra("newspaper_issue_number", item.issueNumber)
                }
            }
        }
        startActivity(intent)
    }
    private fun setupSwipeToDelete() {
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false

            override fun onSwiped(vh: RecyclerView.ViewHolder, direction: Int) {
                val item = libraryAdapter.currentList[vh.adapterPosition]
                viewModel.removeItem(item)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
