package app.melum.ui.artist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.melum.R
import app.melum.common.BaseFragment
import app.melum.common.RecyclerBindingAdapter
import app.melum.entities.Album
import kotlinx.android.synthetic.main.artist_details_fragment.*
import kotlin.reflect.KClass

class ArtistDetailsFragment : BaseFragment<ArtistDetailsViewModel>() {

    override val viewModelClass: KClass<ArtistDetailsViewModel> = ArtistDetailsViewModel::class

    override val layoutId: Int = R.layout.artist_details_fragment

    private val albumsAdapter = RecyclerBindingAdapter<Album>(R.layout.item_album)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val artist = ArtistDetailsFragmentArgs.fromBundle(arguments ?: bundleOf()).artist
        viewModel.setInput(artist)

        rvAlbums.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = albumsAdapter.also {
                it.setOnItemClickListener { _, item ->
                    navController.navigate(
                        ArtistDetailsFragmentDirections.toAlbumFragment(
                            item,
                            artist
                        )
                    )
                }
            }
        }
        btnPlay.setOnClickListener {
            //todo:play
        }
        btnBack.setOnClickListener {
            navController.popBackStack()
        }
        btnShare.setOnClickListener {
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

    }

    override fun observeLiveData() {
        viewModel.albums.observe(this, Observer {
            albumsAdapter.items = it.data.toMutableList()
        })
    }

}