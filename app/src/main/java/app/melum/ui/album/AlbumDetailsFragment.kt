package app.melum.ui.album

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.melum.R
import app.melum.common.BaseFragment
import app.melum.common.RecyclerBindingAdapter
import app.melum.entities.Song
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.album_details_fragment.*


class AlbumDetailsFragment : BaseFragment<AlbumDetailsViewModel>(AlbumDetailsViewModel::class) {

    override val layoutId: Int = R.layout.album_details_fragment

    private val songsAdapter = RecyclerBindingAdapter<Song>(R.layout.item_song)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val album = AlbumDetailsFragmentArgs.fromBundle(arguments ?: bundleOf()).album
        val artist = AlbumDetailsFragmentArgs.fromBundle(arguments ?: bundleOf()).artist
        val isSaved = AlbumDetailsFragmentArgs.fromBundle(arguments ?: bundleOf()).isSaved

        viewModel.setInput(album, artist, isSaved)

        btnSave.setOnClickListener {
            viewModel.save()
        }
        btnRemove.setOnClickListener {
            viewModel.remove()
        }
        toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }

        initFadeoutArtist()
        rvSongs.run {
            layoutManager = LinearLayoutManager(context)
            adapter = songsAdapter.also {
                it.setOnItemClickListener { pos, song ->
                    song.stream.let {
                        Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    }.takeIf {
                        activity != null && it.resolveActivity(activity!!.packageManager) != null
                    }?.run {
                        startActivity(this)
                    }
                }
            }
        }
    }

    private fun initFadeoutArtist() {
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            tvArtistName.alpha = 1.0f - Math.abs(
                verticalOffset / appBarLayout.totalScrollRange.toFloat()
            )
        })
    }

    override fun observeLiveData() {
        viewModel.songs.observe(this, Observer {
            songsAdapter.items = it.toMutableList()
        })

        viewModel.savedEvent.observe(this, Observer {
            navController.navigate(AlbumDetailsFragmentDirections.toHomeFragment())
        })
    }

}