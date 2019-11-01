package app.melum.ui.explore

import android.os.Bundle
import android.view.View
import app.melum.R
import app.melum.base.BaseFragment
import kotlinx.android.synthetic.main.explore_fragment.*
import kotlin.reflect.KClass

class ExploreFragment : BaseFragment<ExploreViewModel>() {
    override val viewModelClass: KClass<ExploreViewModel> = ExploreViewModel::class
    override val layoutId: Int = R.layout.explore_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnNext.setOnClickListener {
           navController.navigate(ExploreFragmentDirections.toArtistFragment())
        }
    }

    override fun observeLiveData() {

    }

}