package com.example.oop2.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop2.R
import com.example.oop2.libra.LibraryAdapter
import com.example.oop2.libra.LibraryViewModel
import com.example.oop2.models.LibraryItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {

    interface OnItemClickListener {
        fun onItemClicked(item: LibraryItem?, action: LibraryActionType)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LibraryAdapter
    private lateinit var viewModel: LibraryViewModel
    private lateinit var addButton: FloatingActionButton
    private var listener: OnItemClickListener? = null

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        if (context is OnItemClickListener) {
            listener = context
        } else {
            throw ClassCastException("Activity must implement OnItemClickListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        addButton = view.findViewById(R.id.add_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setBackgroundDrawable(
            ContextCompat.getDrawable(requireContext(), R.color.purple_200)
        )

        viewModel = ViewModelProvider(requireActivity())[LibraryViewModel::class.java]
        adapter = LibraryAdapter { item ->
            listener?.onItemClicked(item, LibraryActionType.VIEW)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.libraryItems.observe(viewLifecycleOwner) { itemList ->
            adapter.submitList(itemList)
        }

        addButton.setOnClickListener {
            listener?.onItemClicked(null, LibraryActionType.ADD)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sort, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                showSortPopup()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSortPopup() {
        val anchor = requireActivity().findViewById<View>(R.id.action_sort)
        val popup = PopupMenu(requireContext(), anchor)
        popup.menuInflater.inflate(R.menu.menu_sort_popup, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sort_by_name -> viewModel.changeSorting(true)
                R.id.sort_by_date -> viewModel.changeSorting(false)
            }
            true
        }
        popup.show()
    }
}
