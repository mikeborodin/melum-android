package app.melum.ui.splash

import android.content.Intent
import android.os.Bundle
import app.melum.R
import app.melum.common.BaseActivity
import app.melum.ui.main.MainActivity

class SplashActivity : BaseActivity<SplashViewModel>(SplashViewModel::class) {
    override val layoutId: Int = R.layout.activity_splash

    override fun observeLiveData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}