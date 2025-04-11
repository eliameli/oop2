package com.example.oop2.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.oop2.R
import com.example.oop2.models.*

class AddItemActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Добавление объектов"
        setContentView(R.layout.activity_add_item)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.purple_200)))
        val icon = findViewById<ImageView>(R.id.add)
        val nameInput = findViewById<EditText>(R.id.edit_item_name)
        val typeSpinner = findViewById<Spinner>(R.id.type_spinner)
        val authorInput = findViewById<EditText>(R.id.edit_author)
        val pagesInput = findViewById<EditText>(R.id.edit_pages)
        val diskTypeSpinner = findViewById<Spinner>(R.id.disk_type_spinner)
        val issueInput = findViewById<EditText>(R.id.edit_issue_number)
        val monthInput = findViewById<EditText>(R.id.edit_month)
        val saveButton = findViewById<Button>(R.id.save_button)

        val itemTypes = listOf("Book", "Disk", "Newspaper")
        typeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemTypes)

        val diskTypes = DiskType.values().map { it.name }
        diskTypeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, diskTypes)

        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selected = itemTypes[position]
                authorInput.visibility = if (selected == "Book") View.VISIBLE else View.GONE
                pagesInput.visibility = if (selected == "Book") View.VISIBLE else View.GONE
                diskTypeSpinner.visibility = if (selected == "Disk") View.VISIBLE else View.GONE
                issueInput.visibility = if (selected == "Newspaper") View.VISIBLE else View.GONE
                monthInput.visibility = if (selected == "Newspaper") View.VISIBLE else View.GONE

                icon.setImageResource(
                    when (selected) {
                        "Book" -> R.drawable.ic_book
                        "Disk" -> R.drawable.ic_disk
                        "Newspaper" -> R.drawable.ic_newspaper
                        else -> R.drawable.ic_default
                    }
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        saveButton.setOnClickListener {
            val name = nameInput.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(this, "Введите название", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // рандомное значение айди после создания элемента
            val selected = typeSpinner.selectedItem.toString()
            val item = when (selected) {
                "Book" -> {
                    val author = authorInput.text.toString()
                    val pages = pagesInput.text.toString().toIntOrNull() ?: 100
                    Book((0..99999).random(), true, name, author, pages)
                }
                "Disk" -> {
                    val diskType = DiskType.valueOf(diskTypeSpinner.selectedItem.toString())
                    Disk((0..99999).random(), true, name, diskType)
                }
                else -> {
                    val issue = issueInput.text.toString().toIntOrNull() ?: 1
                    val month = monthInput.text.toString().toIntOrNull() ?: 1
                    Newspaper((0..99999).random(), true, name, issue, month)
                }
            }

            val resultIntent = Intent().apply {
                putExtra("new_item", item)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
