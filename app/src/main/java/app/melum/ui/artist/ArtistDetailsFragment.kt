package app.melum.ui.artist

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import app.melum.R
import app.melum.common.BaseFragment
import app.melum.common.RecyclerBindingAdapter
import app.melum.entities.Album
import app.melum.entities.Artist
import kotlinx.android.synthetic.main.artist_details_fragment.*

class ArtistDetailsFragment : BaseFragment<ArtistDetailsViewModel>(ArtistDetailsViewModel::class) {

    override val layoutId: Int = R.layout.artist_details_fragment

    private val albumsAdapter = RecyclerBindingAdapter<Album>(R.layout.item_album)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val artist = ArtistDetailsFragmentArgs.fromBundle(arguments ?: bundleOf()).artist
        viewModel.setInput(artist)
        initList(artist)
        setListeners()
    }

    private fun initList(artist: Artist) {
        rvAlbums.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = albumsAdapter.also {
                it.setOnItemClickListener { pos, item ->
                    openDetails(pos, item, artist)
                }
            }
        }
    }

    private fun setListeners() {
        btnPlay.setOnClickListener {
            shareArtist()
        }
        btnShare.setOnClickListener {
            openArtistUrl()
        }
        btnBack.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun openArtistUrl() {
        val artist = ArtistDetailsFragmentArgs.fromBundle(arguments ?: bundleOf()).artist
        Intent(Intent.ACTION_VIEW, Uri.parse(artist.link)).takeIf {
            activity != null && it.resolveActivity(activity!!.packageManager) != null
        }?.run {
            startActivity(
                Intent.createChooser(this, "Share ${artist.name} to...")
            )
        }
    }


    private fun shareArtist() {
        val artist = ArtistDetailsFragmentArgs.fromBundle(arguments ?: bundleOf()).artist
        artist.link.let {
            Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, "Hey! Listen this on LastFM: ${artist.link}")
                type = "text/plain"
            }
        }.takeIf {
            activity != null && it.resolveActivity(activity!!.packageManager) != null
        }?.run {
            startActivity(
                Intent.createChooser(this, "Share ${artist.name} to...")
            )
        }
    }

    private fun openDetails(pos: Int, item: Album, artist: Artist) {
        navController.navigate(
            R.id.toAlbumFragment, bundleOf(
                "album" to item,
                "artist" to artist,
                "position" to pos
            ), null,
            rvAlbums.findViewHolderForLayoutPosition(pos)
                ?.itemView?.findViewById<ImageView>(R.id.ivAlbumCover)
                ?.takeIf { Build.VERSION.SDK_INT >= 21 }
                ?.let {
                    it to ViewCompat.getTransitionName(it)!!
                }?.let {
                    FragmentNavigatorExtras(it)
                }
        )
    }

    override fun observeLiveData() {
        viewModel.albums.observe(this, Observer {
            albumsAdapter.items = it.toMutableList()
        })
    }

}