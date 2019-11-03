package app.melum

import android.app.Activity
import android.app.Application
import android.os.Bundle

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