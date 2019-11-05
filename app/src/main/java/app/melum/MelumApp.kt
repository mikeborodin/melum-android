package app.melum

import android.app.Activity
import android.app.Application
import android.os.Handler
import app.melum.common.CustomActivityLifecycleCallback
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
        //needed for app to listen connectivity state
        this.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityResumed(activity: Activity?) {
        super.onActivityResumed(activity)
        activity?.let {
            currentActivityReference = WeakReference(it)
            checkIfForeground()
        }
    }

    override fun onActivityPaused(activity: Activity?) {
        super.onActivityPaused(activity)
        currentActivityReference = null
        onBackground()
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

    private fun checkIfForeground() {
        if (isApplicationInBackground.get()) {
            onForeground()
            isApplicationInBackground.set(false)
        }
    }

    private fun onForeground() {
        val connectedManager: ConnectedManager by inject()
        connectedManager.startListening()
    }

    private fun onBackground() {
        handler?.removeCallbacksAndMessages(null)
        val connectedManager: ConnectedManager by inject()
        connectedManager.stopListening()
        isApplicationInBackground.set(true)
    }

    companion object {
        private val isApplicationInBackground = AtomicBoolean(true)
        private var currentActivityReference: WeakReference<Activity>? = null
    }
}