package app.melum.ui

import app.melum.R
import app.melum.common.BaseActivity
import kotlin.reflect.KClass

class MainActivity : BaseActivity<MainViewModel>() {

    override val viewModelClass: KClass<MainViewModel> = MainViewModel::class

    override val layoutId: Int = R.layout.activity_main

    override fun observeLiveData() {

    }

}
