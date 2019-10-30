package app.melum

import android.app.Application
import app.melum.di.networkModule
import org.koin.core.context.startKoin

class MelumApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin{
            modules(networkModule)
        }
    }
}