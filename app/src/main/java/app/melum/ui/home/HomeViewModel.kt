package app.melum.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.melum.abstractions.Repository
import app.melum.common.BaseViewModel
import app.melum.entities.Album

class HomeViewModel(private val repo: Repository) : BaseViewModel() {

    private val _savedAlbums = MutableLiveData<List<Album>>()
    val savedAlbums: LiveData<List<Album>> = _savedAlbums

    private val _isEmptyState = MutableLiveData<Boolean>()
    val isEmptyState: LiveData<Boolean> = _isEmptyState

    fun load() {
        launchHandled({
            repo.getSavedAlbums()
        }, {
            _savedAlbums.postValue(it)
            _isEmptyState.postValue(it.isEmpty())
        })
    }

}