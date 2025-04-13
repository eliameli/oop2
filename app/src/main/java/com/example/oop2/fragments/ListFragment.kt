package com.example.oop2.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
        fun onItemClicked(item: LibraryItem?)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LibraryAdapter
    private lateinit var viewModel: LibraryViewModel
    private lateinit var addButton: FloatingActionButton
    private var listener: OnItemClickListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemClickListener) listener = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        addButton = view.findViewById(R.id.add_button)
        return view
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.purple_200, null))
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[LibraryViewModel::class.java]
        adapter = LibraryAdapter { item -> listener?.onItemClicked(item) }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { itemList ->
            adapter.submitList(itemList.toList())
        }

        addButton.setOnClickListener {
            listener?.onItemClicked(null) // Передаём null для нового элемента
        }
    }
}
