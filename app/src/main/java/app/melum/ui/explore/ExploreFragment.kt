package app.melum.ui.explore

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.melum.R
import app.melum.common.BaseFragment
import app.melum.common.RecyclerBindingAdapter
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
            viewModel.search("Jimmy Eat World")
        }
        rvArtists.run {
            adapter = artistsAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }

    override fun observeLiveData() {
        viewModel.searchResults.observe(this, Observer {
            artistsAdapter.items = it.toMutableList()
        })
    }

}