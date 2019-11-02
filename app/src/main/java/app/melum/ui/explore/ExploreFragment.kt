package app.melum.ui.explore

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.melum.R
import app.melum.common.BaseFragment
import app.melum.common.RecyclerBindingAdapter
import app.melum.common.hideKeyboard
import app.melum.entities.Artist
import kotlinx.android.synthetic.main.explore_fragment.*
import kotlin.reflect.KClass


class ExploreFragment : BaseFragment<ExploreViewModel>() {
    override val viewModelClass: KClass<ExploreViewModel> = ExploreViewModel::class
    override val layoutId: Int = R.layout.explore_fragment

    private val artistsAdapter = RecyclerBindingAdapter<Artist>(R.layout.item_artist_result)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnNext.setOnClickListener {
            viewModel.search()
            hideKeyboard()
        }

        etSearch.requestFocus()
        val imm =
            getSystemService<InputMethodManager>(etSearch.context, InputMethodManager::class.java)
        imm!!.showSoftInput(etSearch, SHOW_IMPLICIT)

        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search()
                hideKeyboard()
                true
            }
            false
        }

        rvArtists.run {
            adapter = artistsAdapter.also {
                it.setOnItemClickListener { pos, item ->
                    navController.navigate(ExploreFragmentDirections.toArtistFragment(item))
                }
            }
            layoutManager = LinearLayoutManager(context)
        }
        btnBack.setOnClickListener {
            navController.popBackStack()
        }


    }

    override fun observeLiveData() {
        viewModel.searchResults.observe(this, Observer {
            artistsAdapter.items = it.toMutableList()
        })
    }

}