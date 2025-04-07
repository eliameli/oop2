package com.example.oop2.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.oop2.R
import com.example.oop2.models.*
import java.io.Serializable

@Suppress("DEPRECATION")
class ItemDetailsActivity : AppCompatActivity() {

    private var isNewItem: Boolean = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        val imageView = findViewById<ImageView>(R.id.item_icon)
        val infoText = findViewById<TextView>(R.id.item_id)
        val saveButton = findViewById<Button>(R.id.save_button)

        isNewItem = intent.getBooleanExtra("isNewItem", false)

        if (isNewItem) {
            setupAddNewItemUI()
        } else {

            val item = intent.getSerializableExtra("item") as? LibraryItem
            if (item == null) {
                finish()
                return
            }
            val iconResId = item.iconResId
            if (iconResId == 0) {
                Toast.makeText(this, "Ошибка: неправильный тип объекта", Toast.LENGTH_SHORT).show()
                finish()
                return
            }

            imageView.setImageResource(iconResId)
            infoText.text = item.getDetailedInfo()
            saveButton.visibility = Button.GONE

        }
    }

    private fun setupAddNewItemUI() {
        val nameInput = findViewById<EditText>(R.id.name_input)
        val typeSpinner = findViewById<Spinner>(R.id.type_spinner)
        val saveButton = findViewById<Button>(R.id.save_button)

        typeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("Книга", "Диск", "Газета")
        )

        saveButton.setOnClickListener {
            val name = nameInput.text.toString()
            val type = typeSpinner.selectedItem.toString()

            if (name.isBlank()) {
                Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newItem: LibraryItem = when (type) {
                "Книга" -> Book((0..10000).random(), true, name, "Автор", 100)
                "Диск" -> Disk((0..10000).random(), true, name, DiskType.CD)
                else -> Newspaper((0..10000).random(), true, name, 1, 2024)
            }

            val resultIntent = Intent()
            resultIntent.putExtra("newItem", newItem as Serializable)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
