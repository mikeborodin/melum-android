package app.melum.ui.album

import android.os.Bundle
import android.view.View
import app.melum.R
import app.melum.common.BaseFragment
import kotlinx.android.synthetic.main.album_details_fragment.*
import kotlin.reflect.KClass

class AlbumDetailsFragment : BaseFragment<AlbumDetailsViewModel>() {
    override val viewModelClass: KClass<AlbumDetailsViewModel> = AlbumDetailsViewModel::class
    override val layoutId: Int = R.layout.album_details_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSave.setOnClickListener {
            navController.navigate(AlbumDetailsFragmentDirections.toHomeFragment())
        }
    }

    override fun observeLiveData() {
    }

}