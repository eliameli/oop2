package com.example.oop2.activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.oop2.R
import com.example.oop2.models.*

@Suppress("DEPRECATION")
class ItemDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        val icon = findViewById<ImageView>(R.id.item_icon)

        val saveBtn = findViewById<Button>(R.id.save_button)

        val item = intent.getSerializableExtra("item") as? LibraryItem
        val isNewItem = intent.getBooleanExtra("isNewItem", false)

        if (isNewItem) {

            saveBtn.visibility = Button.VISIBLE
        }

        item?.let {
            icon.setImageResource(it.getIconResId())
            findViewById<TextView>(R.id.item_info).text = item.getBriefInfo()

        }
    }
}
