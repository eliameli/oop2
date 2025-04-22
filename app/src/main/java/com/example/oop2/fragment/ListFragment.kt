package com.example.oop2.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop2.R
import com.example.oop2.libra.LibraryAdapter
import com.example.oop2.libra.LibraryViewModel
import com.example.oop2.models.LibraryItem
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {

    interface OnItemClickListener {
        fun onItemClicked(item: LibraryItem?, action: LibraryActionType)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LibraryAdapter
    private lateinit var viewModel: LibraryViewModel
    private lateinit var addButton: FloatingActionButton
    private lateinit var shimmer: ShimmerFrameLayout
    private var listener: OnItemClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemClickListener) {
            listener = context
        } else {
            throw ClassCastException("Activity must implement OnItemClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        addButton = view.findViewById(R.id.add_button)
        shimmer = view.findViewById(R.id.shimmer)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (requireActivity() as? AppCompatActivity)?.supportActionBar
            ?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.color.purple_200))
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[LibraryViewModel::class.java]
        adapter = LibraryAdapter { item -> listener?.onItemClicked(item, LibraryActionType.VIEW) }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { itemList ->
            if (itemList.isEmpty()) {
                shimmer.startShimmer()
                shimmer.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                shimmer.stopShimmer()
                shimmer.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                adapter.submitList(itemList)
            }
        }
        val retryButton: Button = view.findViewById(R.id.retry_button)

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            if (errorMsg != null) {
                shimmer.stopShimmer()
                shimmer.visibility = View.GONE
                recyclerView.visibility = View.GONE
                retryButton.visibility = View.VISIBLE

                Toast.makeText(requireContext(), "Ошибка: $errorMsg", Toast.LENGTH_LONG).show()
            } else {
                retryButton.visibility = View.GONE
            }
        }

        retryButton.setOnClickListener {
            shimmer.startShimmer()
            shimmer.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            viewModel.loadItems()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(requireContext(), "Ошибка: $it", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.loadItems()

        addButton.setOnClickListener {
            listener?.onItemClicked(null, LibraryActionType.ADD)
        }
    }
}
