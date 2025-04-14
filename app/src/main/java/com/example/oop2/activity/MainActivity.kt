package com.example.oop2.activity
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.oop2.R
import com.example.oop2.fragments.DetailsFragment
import com.example.oop2.fragments.ListFragment
import com.example.oop2.models.LibraryItem
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), ListFragment.OnItemClickListener {
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        if (isLandscape) {
            val detailsFragment = supportFragmentManager.findFragmentById(R.id.fragment_details_container)
            if (detailsFragment != null) {
                supportFragmentManager.beginTransaction()
                    .remove(detailsFragment)
                    .commit()
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tag = "details"
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val existingDetailsArgs = supportFragmentManager.findFragmentByTag(tag)?.arguments
        val existingDetails = existingDetailsArgs?.let {
            DetailsFragment().apply { arguments = it }
        }
        //удаление фрагментов при переходе в ландскейп
        if (isLandscape) {
            supportFragmentManager.findFragmentById(R.id.fragment_container)?.let {
                supportFragmentManager.beginTransaction().remove(it).commitNow()
            }
        }
        if (isLandscape) {
            // альбомная ариентация
            if (supportFragmentManager.findFragmentById(R.id.fragment_list_container) == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_list_container, ListFragment())
                    .commit()
            }
            if (existingDetails is DetailsFragment) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_details_container, existingDetails, tag)
                    .commit()
            }
        } else {
            // портретка
            if (existingDetails is DetailsFragment) {
                supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, existingDetails, tag)
                    .addToBackStack(null)
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ListFragment())
                    .commit()
            }
        }
    }
    override fun onItemClicked(item: LibraryItem?) {
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val fragment = DetailsFragment.newInstance(item)
        val tag = "details"

        supportFragmentManager.beginTransaction()
            .replace(
                if (isLandscape) R.id.fragment_details_container else R.id.fragment_container,
                fragment, tag)
            .apply {
                if (!isLandscape) addToBackStack(null)
            }
            .commit()
    }
}
