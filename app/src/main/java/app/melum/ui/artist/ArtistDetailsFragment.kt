package app.melum.ui.artist

import android.os.Bundle
import android.view.View
import app.melum.R
import app.melum.base.BaseFragment
import app.melum.ui.explore.ExploreFragmentDirections
import kotlinx.android.synthetic.main.explore_fragment.*
import kotlin.reflect.KClass

class ArtistDetailsFragment : BaseFragment<ArtistDetailsViewModel>(){

    override val viewModelClass: KClass<ArtistDetailsViewModel> = ArtistDetailsViewModel::class

    override val layoutId: Int = R.layout.artist_details_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnNext.setOnClickListener {
            navController.navigate(ArtistDetailsFragmentDirections.toAlbumFragment())
        }
    }

    override fun observeLiveData() {

    }

}