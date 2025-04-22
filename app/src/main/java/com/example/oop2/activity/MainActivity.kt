package com.example.oop2.activity
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.oop2.R
import com.example.oop2.fragment.DetailsFragment
import com.example.oop2.fragment.LibraryActionType
import com.example.oop2.fragment.ListFragment
import com.example.oop2.models.LibraryItem

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), ListFragment.OnItemClickListener {
    companion object {
        private const val TAG_DETAILS = "details"
    }
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

        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val existingDetailsArgs = supportFragmentManager.findFragmentByTag(TAG_DETAILS)?.arguments
        val existingDetails = existingDetailsArgs?.let {
            DetailsFragment().apply { arguments = it }
        }
        if (isLandscape) {
            supportFragmentManager.findFragmentById(R.id.fragment_container)?.let {
                supportFragmentManager.beginTransaction().remove(it).commitNow()
            }
        }
        if (isLandscape) {
            if (supportFragmentManager.findFragmentById(R.id.fragment_list_container) == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_list_container, ListFragment())
                    .commit()
            }
            if (existingDetails is DetailsFragment) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_details_container, existingDetails, TAG_DETAILS)
                    .commit()
            }
        } else {
            if (existingDetails is DetailsFragment) {
                supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, existingDetails, TAG_DETAILS)
                    .addToBackStack(null)
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ListFragment())
                    .commit()
            }
        }
    }
    override fun onItemClicked(item: LibraryItem?, action: LibraryActionType) {
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val fragment = DetailsFragment.newInstance(item)


        supportFragmentManager.beginTransaction()
            .replace(
                if (isLandscape) R.id.fragment_details_container else R.id.fragment_container,
                fragment, TAG_DETAILS)
            .apply {
                if (!isLandscape) addToBackStack(null)
            }
            .commit()
    }
}
