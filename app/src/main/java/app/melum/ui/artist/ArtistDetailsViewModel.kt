package app.melum.ui.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.melum.abstractions.Repository
import app.melum.common.BaseViewModel
import app.melum.data.network.images.ImageSearch
import app.melum.entities.Album
import app.melum.entities.Artist
import app.melum.entities.PagedResults

class ArtistDetailsViewModel(private val repo: Repository, private val imageSearch: ImageSearch) :
    BaseViewModel() {

    private val _artist = MutableLiveData<Artist>()
    val artist: LiveData<Artist> = _artist


    private val _albums = MutableLiveData<PagedResults<List<Album>>>()
    val albums: LiveData<PagedResults<List<Album>>> = _albums

    private val _image = MutableLiveData<String>()
    val image: LiveData<String> = _image

    fun setInput(artist: Artist) {
        _artist.value = artist

        launchHandled({
            repo.getArtistTopAlbums(artist.name)
        }, {
            _albums.postValue(it)
        })

        launchHandled({
            imageSearch.getImageUrl(artist.id)
        }, {
            _image.postValue(it)
        })

        //todo: will call getArtistTopAlbums
    }


}
