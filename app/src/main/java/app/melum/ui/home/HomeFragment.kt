package app.melum.ui.home

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import app.melum.R
import app.melum.common.BaseFragment
import app.melum.common.RecyclerBindingAdapter
import app.melum.entities.Album
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class) {

    override val layoutId: Int = R.layout.home_fragment

    private val songsAdapter = RecyclerBindingAdapter<Album>(R.layout.item_saved_album)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.load()
        initList()
        setListeners()
    }

    override fun observeLiveData() {
        viewModel.savedAlbums.observe(this, Observer {
            songsAdapter.items = it.toMutableList()
        })
    }

    private fun initList() {
        rvSavedAlbums.run {
            layoutManager = GridLayoutManager(context, 2)
            adapter = songsAdapter.also {
                it.setOnItemClickListener { pos, item ->
                    openDetails(pos, item)
                }
            }
        }
    }

    private fun setListeners() {
        btnExplore.setOnClickListener {
            navController.navigate(HomeFragmentDirections.toExploreFragment())
        }
    }

    private fun openDetails(pos: Int, item: Album) {
        navController.navigate(
            R.id.toAlbumFragment, bundleOf(
                "album" to item,
                "artist" to item.artist,
                "position" to pos,
                "isSaved" to true
            ), null,
            rvSavedAlbums.findViewHolderForLayoutPosition(pos)
                ?.itemView?.findViewById<ImageView>(R.id.ivAlbumCover)
                ?.takeIf { Build.VERSION.SDK_INT >= 21 }
                ?.let {
                    it to ViewCompat.getTransitionName(it)!!
                }?.let {
                    FragmentNavigatorExtras(it)
                }
        )
    }

}