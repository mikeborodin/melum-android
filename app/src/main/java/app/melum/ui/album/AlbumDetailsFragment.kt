package app.melum.ui.album

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.melum.R
import app.melum.common.BaseFragment
import app.melum.common.RecyclerBindingAdapter
import app.melum.entities.Song
import kotlinx.android.synthetic.main.album_details_fragment.*
import kotlin.reflect.KClass

class AlbumDetailsFragment : BaseFragment<AlbumDetailsViewModel>() {
    override val viewModelClass: KClass<AlbumDetailsViewModel> = AlbumDetailsViewModel::class
    override val layoutId: Int = R.layout.album_details_fragment

    private val songsAdapter = RecyclerBindingAdapter<Song>(R.layout.item_song)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val album = AlbumDetailsFragmentArgs.fromBundle(arguments ?: bundleOf()).album
        val artist = AlbumDetailsFragmentArgs.fromBundle(arguments ?: bundleOf()).artist

        viewModel.setInput(album)

        btnSave.setOnClickListener {
            navController.navigate(AlbumDetailsFragmentDirections.toHomeFragment())
        }
        btnBack.setOnClickListener {
            navController.popBackStack()
        }
        rvSongs.run {
            layoutManager = LinearLayoutManager(context)
            adapter = songsAdapter.also {
                it.setOnItemClickListener { pos, song ->
                    //todo: play song
                }
            }
        }
    }

    override fun observeLiveData() {
        viewModel.songs.observe(this, Observer {
            songsAdapter.items = it.toMutableList()
        })
    }

}