package app.melum.ui.explore

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.melum.abstractions.Repository
import app.melum.common.BaseViewModel
import app.melum.entities.Artist

class ExploreViewModel(private val repo: Repository) : BaseViewModel() {

    private val _searchResults = MutableLiveData<List<Artist>>()
    val searchResults: LiveData<List<Artist>> = _searchResults

    private val _isEmptyState = MutableLiveData<Boolean>()
        .apply { value = false }

    val isEmptyState: LiveData<Boolean> = _isEmptyState


    val query = ObservableField<String>("")

    fun search() {
        val q = query.get()?.takeIf { it.isNotBlank() } ?: return
        query.set("")
        launchHandled({ repo.searchArtists(q) }, {
            _searchResults.postValue(it)
            _isEmptyState.postValue(it.isEmpty())
        })
    }
}