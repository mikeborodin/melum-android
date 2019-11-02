package app.melum.ui.home

import android.os.Bundle
import android.view.View
import app.melum.R
import app.melum.common.BaseFragment
import kotlinx.android.synthetic.main.home_fragment.*
import kotlin.reflect.KClass

class HomeFragment : BaseFragment<HomeViewModel>() {

    override val layoutId: Int = R.layout.home_fragment

    override val viewModelClass: KClass<HomeViewModel> = HomeViewModel::class

    override fun observeLiveData() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnExplore.setOnClickListener {
            navController.navigate(HomeFragmentDirections.toExploreFragment())
        }
    }

}