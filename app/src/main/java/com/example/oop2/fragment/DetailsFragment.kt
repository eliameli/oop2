package com.example.oop2.fragment

import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.oop2.R
import com.example.oop2.libra.LibraryViewModel
import com.example.oop2.models.*
import com.facebook.shimmer.ShimmerFrameLayout

class DetailsFragment : Fragment() {

    companion object {
        private const val ARG_ITEM = "arg_item"
        fun newInstance(item: LibraryItem?): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putSerializable(ARG_ITEM, item)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: LibraryViewModel
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_item_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[LibraryViewModel::class.java]
        val item: LibraryItem? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            arguments?.getSerializable(ARG_ITEM, LibraryItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable(ARG_ITEM) as? LibraryItem
        }

        val icon: ImageView = view.findViewById(R.id.item_icon)
        val mainFirst: TextView = view.findViewById(R.id.main_first)
        val mainSecond: TextView = view.findViewById(R.id.main_second)
        val addFirst: TextView = view.findViewById(R.id.add_first)
        val addSecond: TextView = view.findViewById(R.id.add_second)
        val layout: LinearLayout = view.findViewById(R.id.item_detail_layout)
        if (item == null) {
            mainFirst.text = getString(R.string.add_new_item)
            mainSecond.text = ""
            addFirst.text = ""
            addSecond.text = ""

            val nameInput = EditText(requireContext()).apply { hint = "Название" }
            val authorInput = EditText(requireContext()).apply { hint = "Автор" }
            val pagesInput = EditText(requireContext()).apply {
                hint = "Страницы"
                inputType = InputType.TYPE_CLASS_NUMBER
            }
            val issueInput = EditText(requireContext()).apply {
                hint = "Выпуск"
                inputType = InputType.TYPE_CLASS_NUMBER
            }
            val monthInput = EditText(requireContext()).apply {
                hint = "Месяц"
                inputType = InputType.TYPE_CLASS_NUMBER
            }
            val diskTypeSpinner = Spinner(requireContext()).apply {
                adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, DiskType.entries)
            }
            val typeOptions = listOf("Книга", "Диск", "Газета")
            val typeSpinner = Spinner(requireContext()).apply {
                adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, typeOptions)
            }
            val saveButton = Button(requireContext()).apply {

                text = "Сохранить"
                setOnClickListener {
                    val name = nameInput.text.toString().ifBlank { "Без названия" }
                    val id = (1000..9999).random()
                    val selectedType = typeSpinner.selectedItem.toString()
                    val newItem: LibraryItem = when (selectedType) {
                        "Книга" -> Book(id, true, name, authorInput.text.toString(), pagesInput.text.toString().toIntOrNull() ?: 0)
                        "Диск" -> Disk(id, true, name, diskTypeSpinner.selectedItem as DiskType)
                        else -> {
                            val issue = issueInput.text.toString().toIntOrNull() ?: 0
                            val month = monthInput.text.toString().toIntOrNull()?.takeIf { it in 1..12 } ?: 1
                            Newspaper(id, true, name, issue, month)
                        }

                    }
                    viewModel.addItem(newItem)
                    if (resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
                        parentFragmentManager.beginTransaction()
                            .remove(this@DetailsFragment)
                            .commit()
                    } else {
                        parentFragmentManager.popBackStack()
                    }

                }
            }

            typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, v: View?, position: Int, id: Long) {
                    layout.removeAllViews()
                    layout.addView(icon)
                    layout.addView(mainFirst)
                    layout.addView(mainSecond)
                    layout.addView(addFirst)
                    layout.addView(addSecond)
                    layout.addView(nameInput)
                    layout.addView(typeSpinner)
                    when (position) {
                        0 -> {
                            icon.setImageResource(R.drawable.ic_book)
                            layout.addView(authorInput)
                            layout.addView(pagesInput)
                        }
                        1 -> {
                            icon.setImageResource(R.drawable.ic_disk)
                            layout.addView(diskTypeSpinner)
                        }
                        2 -> {
                            icon.setImageResource(R.drawable.ic_newspaper)
                            layout.addView(issueInput)
                            layout.addView(monthInput)
                        }
                    }
                    layout.addView(saveButton)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
            layout.addView(typeSpinner)
            typeSpinner.setSelection(0)
            typeSpinner.onItemSelectedListener?.onItemSelected(typeSpinner, null, 0, 0)
            return

        }
        icon.setImageResource(item.iconResId)
        when (item) {
            is Book -> {
                mainFirst.text = getString(R.string.book_title, item.name)
                mainSecond.text = getString(R.string.book_author, item.author)
                addFirst.text = getString(R.string.book_pages, item.pages)
            }
            is Disk -> {
                mainFirst.text = getString(R.string.disk_title, item.name)
                mainSecond.text = getString(R.string.disk_type, item.diskType.name)
                addFirst.text = ""
            }
            is Newspaper -> {
                mainFirst.text = getString(R.string.newspaper_title, item.name)
                mainSecond.text = getString(R.string.newspaper_issue, item.issueNumber)
                addFirst.text = getString(R.string.newspaper_month, item.month)
            }
        }
        addSecond.text = getString(R.string.item_id, item.id)
    }
}
