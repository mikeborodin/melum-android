package app.melum

import android.app.Activity
import android.app.Application
import android.os.Handler
import app.melum.data.connectivity.ConnectedManager
import app.melum.di.appModule
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean


class MelumApp : Application(), CustomActivityLifecycleCallback, KoinComponent {

    private var handler: Handler? = null


    override fun onCreate() {
        super.onCreate()
        initKoin()
        initRealm()
        this.registerActivityLifecycleCallbacks(this)
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MelumApp)
            modules(appModule)
        }
    }

    private fun initRealm() {
        Realm.init(this)
        val configuration: RealmConfiguration by inject()
        Realm.setDefaultConfiguration(configuration)
    }

    override fun onActivityResumed(activity: Activity?) {
        super.onActivityResumed(activity)
        activity?.let {
            currentActivityReference = WeakReference(it)
            determineForegroundStatus()
        }
    }

    override fun onActivityPaused(activity: Activity?) {
        super.onActivityPaused(activity)
        currentActivityReference = null
        onEnterBackground()
    }

    private fun determineForegroundStatus() {
        if (applicationBackgrounded.get()) {
            onEnterForeground()
            applicationBackgrounded.set(false)
        }
    }

    private fun onEnterForeground() {
        val connectedManager: ConnectedManager by inject()
        connectedManager.startListening()
    }

    private fun onEnterBackground() {
        handler?.removeCallbacksAndMessages(null)
        val connectedManager: ConnectedManager by inject()
        connectedManager.stopListening()
        applicationBackgrounded.set(true)
    }

    companion object {
        private val applicationBackgrounded = AtomicBoolean(true)
        private var currentActivityReference: WeakReference<Activity>? = null
    }
}