package app.melum.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.melum.abstractions.Repository
import app.melum.common.BaseViewModel
import app.melum.entities.Artist

class ExploreViewModel(private val repo: Repository) : BaseViewModel() {

    private val _searchResults = MutableLiveData<List<Artist>>()
    val searchResults: LiveData<List<Artist>> = _searchResults

    fun search(q: String) {
        launchHandled({ repo.searchArtists(q) }, {
            _searchResults.postValue(it.data)
        })
    }
}