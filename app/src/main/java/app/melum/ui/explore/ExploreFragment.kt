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


class ExploreFragment : BaseFragment<ExploreViewModel>(ExploreViewModel::class) {

    override val layoutId: Int = R.layout.explore_fragment

    private val artistsAdapter = RecyclerBindingAdapter<Artist>(R.layout.item_artist_result)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        initList()
    }

    override fun observeLiveData() {
        viewModel.searchResults.observe(this, Observer {
            artistsAdapter.items = it.toMutableList()
        })
    }

    private fun initList() {
        rvArtists.run {
            adapter = artistsAdapter.also {
                it.setOnItemClickListener { pos, item ->
                    hideKeyboard()
                    navController.navigate(ExploreFragmentDirections.toArtistFragment(item))
                }
            }
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setListeners() {
        etSearch.requestFocus()

        getSystemService<InputMethodManager>(
            etSearch.context, InputMethodManager::class.java
        )?.showSoftInput(etSearch, SHOW_IMPLICIT)

        btnNext.setOnClickListener {
            viewModel.search()
            hideKeyboard()
        }
        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search()
                hideKeyboard()
                true
            }
            false
        }
        btnBack.setOnClickListener {
            hideKeyboard()
            navController.popBackStack()
        }
    }


}