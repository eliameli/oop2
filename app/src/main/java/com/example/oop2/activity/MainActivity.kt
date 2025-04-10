package com.example.oop2.activity
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.material.floatingactionbutton.FloatingActionButton

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

        libraryAdapter = LibraryAdapter { item ->
            val intent = Intent(this, ItemDetailsActivity::class.java)
            intent.putExtra("item_type", item.type)
            intent.putExtra("item_icon", item.iconResId)
            intent.putExtra("item_name", item.name)
            intent.putExtra("item_id", item.id)
            if (item is Book) {
                intent.putExtra("book_author", item.author)
                intent.putExtra("book_number_of_pages", item.pages)
            }
            if (item is Disk) {
                intent.putExtra("disk_type", item.diskType.name)
            }
            if (item is Newspaper) {
                intent.putExtra("newspaper_month", item.month)
                intent.putExtra("newspaper_issue_number", item.issueNumber)
            }
            startActivity(intent)
        }
        recyclerView.adapter = libraryAdapter
    val items = loadLibraryItems()
    libraryAdapter.submitList(items)
    setupSwipeToDelete()
        val addButton = findViewById<FloatingActionButton>(R.id.add_button)
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
            Book(16, true, "Звездные войны, Часть 6", "Джордж Лукас", 621),
            Book(16, true, "Жизнь, необыкновенные и удивительные приключения Робинзона Крузо, моряка из Йорка, прожившего 28 лет в полном одиночестве на необитаемом острове у берегов Америки близ устьев реки Ориноко, куда он был выброшен кораблекрушением, во время которого весь экипаж корабля, кроме него, погиб, с изложением его неожиданного освобождения пиратами; написанные им самим.", "Даниель Дефо", 666)
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
                val position = viewHolder.adapterPosition
                val updatedList = libraryAdapter.currentList.toMutableList()
                updatedList.removeAt(position)
                libraryAdapter.submitList(updatedList)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
    private fun openNewItemScreen() {
        val intent = Intent(this, AddItemActivity::class.java)
        addItemLauncher.launch(intent)
    }
    // обновление результата в мейне
    private val addItemLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val newItem = result.data?.getSerializableExtra("new_item") as? LibraryItem ?: return@registerForActivityResult
            val updatedList = libraryAdapter.currentList.toMutableList()
            updatedList.add(newItem)
            libraryAdapter.submitList(updatedList)
        }
    }



}