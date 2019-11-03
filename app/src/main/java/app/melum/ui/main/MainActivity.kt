package app.melum.ui.main

import app.melum.R
import app.melum.common.BaseActivity

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class) {

    override val layoutId: Int = R.layout.activity_main

    override fun observeLiveData() {
    }

    override fun onResume() {
        super.onResume()
    }

}
