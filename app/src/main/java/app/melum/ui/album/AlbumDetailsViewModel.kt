package app.melum.ui.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.melum.abstractions.Repository
import app.melum.common.BaseViewModel
import app.melum.data.connectivity.ConnectedManager
import app.melum.entities.Album
import app.melum.entities.Artist
import app.melum.entities.Song

class AlbumDetailsViewModel(
    private val repo: Repository,
    private val connectedManager: ConnectedManager
) : BaseViewModel() {


    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album> = _album


    private val _artist = MutableLiveData<Artist>()
    val artist: LiveData<Artist> = _artist

    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> = _songs

    private val _position = MutableLiveData<String>()
    val position: LiveData<String> = _position


    private val _savedEvent = MutableLiveData<Unit>()
    val savedEvent: LiveData<Unit> = _savedEvent


    private val _canSave = MutableLiveData<Boolean>().apply { value = true }
    val canSave: LiveData<Boolean> = _canSave


    private val _canRemove = MutableLiveData<Boolean>().apply { value = false }
    val canRemove: LiveData<Boolean> = _canRemove


    private val _isErrorState = MutableLiveData<Boolean>().apply { value = false }
    val isErrorState: LiveData<Boolean> = _isErrorState


    private val _isEmptyState = MutableLiveData<Boolean>().apply { value = false }
    val isEmptyState: LiveData<Boolean> = _isEmptyState


    fun setInput(album: Album, artist: Artist, saved: Boolean) {

        _album.value = album
        _canSave.postValue(!saved)
        _canRemove.postValue(saved)
        _artist.value = artist
        _isErrorState.postValue(false)
        _isEmptyState.postValue(false)
        _songs.postValue(album.songs)

        if (connectedManager.currentValue) {
            launchHandled({
                repo.getAlbumInfo(album.id)
            }, {
                _album.value?.songs = it.songs.toMutableList()
                _album.value?.artist = artist

                _isEmptyState.postValue(it.songs.isEmpty())
                _songs.postValue(it.songs)
            }, {
                _isErrorState.postValue(true)
            })

        }
    }

    fun save() {
        val album = _album.value ?: return
        launchHandled({
            repo.saveAlbum(album)
        }, {
            _canSave.postValue(false)
            _canRemove.postValue(true)
            _savedEvent.postValue(Unit)
        })
    }

    fun remove() {
        val album = _album.value ?: return
        launchHandled({
            repo.deleteSavedAlbum(album.id)
        }, {
            _canSave.postValue(true)
            _canRemove.postValue(false)
            _savedEvent.postValue(Unit)
        })
    }

}