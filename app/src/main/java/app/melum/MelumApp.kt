package app.melum

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Handler
import app.melum.data.connectivity.ConnectedManager
import app.melum.data.network.AddApiKeyInterceptor
import app.melum.di.appModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean

interface CustomActivityLifecycleCallback : Application.ActivityLifecycleCallbacks {

    override fun onActivityResumed(activity: Activity?) {

    }

    override fun onActivityPaused(activity: Activity?) {

    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {

    }

    override fun onActivityDestroyed(activity: Activity?) {

    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity?) {

    }

    override fun onActivityStopped(activity: Activity?) {

    }
}

class MelumApp : Application(), CustomActivityLifecycleCallback, KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        this.registerActivityLifecycleCallbacks(this)
    }

    private fun initKoin() {
        startKoin{
            androidContext(this@MelumApp)
            modules(appModule)
        }
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

    private var handler: Handler? = null


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