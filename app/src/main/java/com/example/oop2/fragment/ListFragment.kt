package com.example.oop2.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop2.R
import com.example.oop2.libra.GoogleBooksAdapter
import com.example.oop2.libra.LibraryAdapter
import com.example.oop2.libra.LibraryViewModel
import com.example.oop2.models.Book
import com.example.oop2.models.LibraryItem
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {

    interface OnItemClickListener {
        fun onItemClicked(item: LibraryItem?, action: LibraryActionType)
    }

    private var listener: OnItemClickListener? = null
    private lateinit var viewModel: LibraryViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var retryButton: Button
    private lateinit var addButton: FloatingActionButton

    private lateinit var libraryButton: Button
    private lateinit var googleButton: Button
    private lateinit var searchSection: View
    private lateinit var authorInput: EditText
    private lateinit var titleInput: EditText
    private lateinit var searchButton: Button

    private var libraryAdapter: LibraryAdapter? = null
    private var googleAdapter: GoogleBooksAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemClickListener) listener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[LibraryViewModel::class.java]

        recyclerView = view.findViewById(R.id.recyclerView)
        shimmer = view.findViewById(R.id.shimmer)
        retryButton = view.findViewById(R.id.retry_button)
        addButton = view.findViewById(R.id.add_button)

        libraryButton = view.findViewById(R.id.button_local)
        googleButton = view.findViewById(R.id.button_google)
        searchSection = view.findViewById(R.id.search_section)
        authorInput = view.findViewById(R.id.input_author)
        titleInput = view.findViewById(R.id.input_title)
        searchButton = view.findViewById(R.id.button_search)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        setupObservers()
        setupListeners()

        showShimmer()
        viewModel.loadInitialItems()
    }

    private fun setupObservers() {
        viewModel.libraryItems.observe(viewLifecycleOwner) {
            libraryAdapter = LibraryAdapter { item ->
                listener?.onItemClicked(item, LibraryActionType.VIEW)
            }
            recyclerView.adapter = libraryAdapter
            libraryAdapter?.submitList(it)
            hideShimmer()
        }

        viewModel.googleBooks.observe(viewLifecycleOwner) { libraryItems ->
            val books = libraryItems.filterIsInstance<Book>() // оставляем только Book
            googleAdapter = GoogleBooksAdapter(
                items = books,
                onLongClick = { book -> viewModel.saveGoogleBook(book) }
            )
            recyclerView.adapter = googleAdapter
            hideShimmer()
        }


        viewModel.loading.observe(viewLifecycleOwner) {
            shimmer.isVisible = it
            recyclerView.isVisible = !it
        }

        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupListeners() {
        addButton.setOnClickListener {
            listener?.onItemClicked(null, LibraryActionType.ADD)
        }

        retryButton.setOnClickListener {
            showShimmer()
            viewModel.refreshItems()
        }

        libraryButton.setOnClickListener {
            searchSection.visibility = View.GONE
            showShimmer()
            viewModel.loadInitialItems()
        }

        googleButton.setOnClickListener {
            searchSection.visibility = View.VISIBLE
            recyclerView.adapter = null
            viewModel.clearGoogleBooks()
        }

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchButton.isEnabled =
                    authorInput.text.length >= 3 || titleInput.text.length >= 3
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        authorInput.addTextChangedListener(watcher)
        titleInput.addTextChangedListener(watcher)

        searchButton.setOnClickListener {
            showShimmer()
            viewModel.searchGoogleBooks(
                author = authorInput.text.toString(),
                title = titleInput.text.toString()
            )
        }
    }

    private fun showShimmer() {
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
        recyclerView.visibility = View.GONE
    }

    private fun hideShimmer() {
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }
}
