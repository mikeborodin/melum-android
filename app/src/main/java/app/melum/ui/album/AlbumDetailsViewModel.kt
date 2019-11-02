package app.melum.ui.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.melum.abstractions.Repository
import app.melum.common.BaseViewModel
import app.melum.entities.Album
import app.melum.entities.Song

class AlbumDetailsViewModel(private val repo: Repository) : BaseViewModel() {


    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album> = _album

    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> = _songs


    fun setInput(album: Album) {
        _album.value = album
        launchHandled({
            repo.getAlbumInfo(album.id)
        }, {
            _songs.postValue(it.songs)
        })
    }

}