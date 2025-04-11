package com.example.oop2.activity

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.oop2.R

class ItemDetailsActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        supportActionBar?.title = "Информация"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.purple_200)))
        val itemType = intent.getStringExtra("item_type")
        val itemIcon = intent.getIntExtra("item_icon", R.drawable.ic_default)
        val itemName = intent.getStringExtra("item_name")
        val itemId = intent.getIntExtra("item_id", -1)
        // связь view из разметки
        val itemIconView: ImageView = findViewById(R.id.item_icon)
        val firstTextView: TextView = findViewById(R.id.main_first)
        val secondTextView: TextView = findViewById(R.id.main_second)
        val addFirstTextView: TextView = findViewById(R.id.add_first)
        val addSecondTextView: TextView = findViewById(R.id.add_second)
        //далее общая информация
        itemIconView.setImageResource(itemIcon)
        // ветки элементов
        when (itemType) {
            "Book" -> {
                val author = intent.getStringExtra("book_author") ?: "Неизвестный автор"
                val numberOfPages = intent.getIntExtra("book_number_of_pages", -1)
                firstTextView.text = "Книга: $itemName"
                secondTextView.text = "Автор: $author"
                addFirstTextView.text = "Страниц: $numberOfPages"
                addSecondTextView.text = "ID: $itemId"
            }
            "Disk" -> {
                firstTextView.text = "Диск: $itemName"
                val diskType = intent.getStringExtra("disk_type")
                secondTextView.text = "Тип диска: $diskType"
                addFirstTextView.text = ""
                addSecondTextView.text = "ID: $itemId"
            }
            "Newspaper" -> {
                firstTextView.text = "Газета: $itemName"
                val monthOfPublication = intent.getIntExtra("newspaper_month", -1)
                val issueNumber = intent.getIntExtra("newspaper_issue_number", -1)
                secondTextView.text = "Выпуск №$issueNumber"
                addFirstTextView.text = "Месяц: $monthOfPublication"
                addSecondTextView.text = "ID: $itemId"
            }
            else -> {
                secondTextView.text = ""
                addFirstTextView.text = ""
                addSecondTextView.text = "ID: $itemId"
            }
        }

    }}