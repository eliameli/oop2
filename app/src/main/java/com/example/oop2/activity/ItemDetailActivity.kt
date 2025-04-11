package com.example.oop2.activity
import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.oop2.R
import com.example.oop2.libra.LibraryRepository
import com.example.oop2.models.*
class ItemDetailsActivity : AppCompatActivity() {
    private lateinit var itemIcon: ImageView
    private lateinit var mainFirst: TextView
    private lateinit var mainSecond: TextView
    private lateinit var addFirst: TextView
    private lateinit var addSecond: TextView
    private lateinit var saveButton: Button
    private lateinit var editItemName: EditText
    private lateinit var typeSpinner: Spinner
    private lateinit var editAuthor: EditText
    private lateinit var editPages: EditText
    private lateinit var editIssueNumber: EditText
    private lateinit var editMonth: EditText
    private lateinit var diskTypeSpinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        supportActionBar?.title = "Информация"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.purple_200)))
        val isNewItem = intent.getBooleanExtra("isNewItem", false)
        itemIcon = findViewById(R.id.item_icon)
        mainFirst = findViewById(R.id.main_first)
        mainSecond = findViewById(R.id.main_second)
        addFirst = findViewById(R.id.add_first)
        addSecond = findViewById(R.id.add_second)
        saveButton = findViewById(R.id.save_button)
        editItemName = findViewById(R.id.edit_item_name)
        typeSpinner = findViewById(R.id.type_spinner)
        editAuthor = findViewById(R.id.edit_author)
        editPages = findViewById(R.id.edit_pages)
        editIssueNumber = findViewById(R.id.edit_issue_number)
        editMonth = findViewById(R.id.edit_month)
        diskTypeSpinner = findViewById(R.id.disk_type_spinner)

        if (isNewItem) {
            setupAddMode()
        } else {
            setupViewMode()
        }
    }
    private fun setupAddMode() {
        // поля ввода
        mainFirst.visibility = View.GONE
        mainSecond.visibility = View.GONE
        addFirst.visibility = View.GONE
        addSecond.visibility = View.GONE
        editItemName.visibility = View.VISIBLE
        saveButton.visibility = View.VISIBLE
        typeSpinner.visibility = View.VISIBLE
        val types = listOf("Книга", "Диск", "Газета")
        typeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)

        // тип диска
        diskTypeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, DiskType.values())

        // спиннер изменения типа при выборе
        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(p: AdapterView<*>?, v: View?, position: Int, id: Long) {
                when (types[position]) {
                    "Книга" -> {
                        itemIcon.setImageResource(R.drawable.ic_book)
                        editAuthor.visibility = View.VISIBLE
                        editPages.visibility = View.VISIBLE
                        diskTypeSpinner.visibility = View.GONE
                        editIssueNumber.visibility = View.GONE
                        editMonth.visibility = View.GONE
                    }
                    "Диск" -> {
                        itemIcon.setImageResource(R.drawable.ic_disk)
                        editAuthor.visibility = View.GONE
                        editPages.visibility = View.GONE
                        diskTypeSpinner.visibility = View.VISIBLE
                        editIssueNumber.visibility = View.GONE
                        editMonth.visibility = View.GONE
                    }
                    "Газета" -> {
                        itemIcon.setImageResource(R.drawable.ic_newspaper)
                        editAuthor.visibility = View.GONE
                        editPages.visibility = View.GONE
                        diskTypeSpinner.visibility = View.GONE
                        editIssueNumber.visibility = View.VISIBLE
                        editMonth.visibility = View.VISIBLE
                    }
                }
            }
        }
        saveButton.setOnClickListener {
            val name = editItemName.text.toString().ifBlank { "Без названия" }
            val type = typeSpinner.selectedItem.toString()
            val id = (1000..9999).random()
            val newItem: LibraryItem = when (type) {
                "Книга" -> Book(id, true, name, editAuthor.text.toString(), editPages.text.toString().toIntOrNull() ?: 0)
                "Диск" -> Disk(id, true, name, diskTypeSpinner.selectedItem as DiskType)
                else -> Newspaper(id, true, name, editIssueNumber.text.toString().toIntOrNull() ?: 0, editMonth.text.toString().toIntOrNull() ?: 1)
            }

            LibraryRepository.addItem(newItem)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
    private fun setupViewMode() {
        editItemName.visibility = View.GONE
        saveButton.visibility = View.GONE
        typeSpinner.visibility = View.GONE
        editAuthor.visibility = View.GONE
        editPages.visibility = View.GONE
        editIssueNumber.visibility = View.GONE
        editMonth.visibility = View.GONE
        diskTypeSpinner.visibility = View.GONE

        val itemType = intent.getStringExtra("item_type")
        val itemIconRes = intent.getIntExtra("item_icon", R.drawable.ic_default)
        val itemName = intent.getStringExtra("item_name")
        val itemId = intent.getIntExtra("item_id", -1)

        itemIcon.setImageResource(itemIconRes)

        when (itemType) {
            "Book" -> {
                val author = intent.getStringExtra("book_author") ?: "Неизвестный"
                val pages = intent.getIntExtra("book_number_of_pages", -1)
                mainFirst.text = "Книга: $itemName"
                mainSecond.text = "Автор: $author"
                addFirst.text = "Страниц: $pages"
                addSecond.text = "ID: $itemId"
            }
            "Disk" -> {
                val diskType = intent.getStringExtra("disk_type")
                mainFirst.text = "Диск: $itemName"
                mainSecond.text = "Тип диска: $diskType"
                addFirst.text = ""
                addSecond.text = "ID: $itemId"
            }
            "Newspaper" -> {
                val issue = intent.getIntExtra("newspaper_issue_number", -1)
                val month = intent.getIntExtra("newspaper_month", -1)
                mainFirst.text = "Газета: $itemName"
                mainSecond.text = "Выпуск №$issue"
                addFirst.text = "Месяц: $month"
                addSecond.text = "ID: $itemId"
            }
            else -> {
                mainFirst.text = itemName ?: "Неизвестно"
                mainSecond.text = ""
                addFirst.text = ""
                addSecond.text = "ID: $itemId"
            }
        }
    }
}
