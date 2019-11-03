package app.melum.common

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import app.melum.BR
import app.melum.R
import app.melum.data.connectivity.ConnectedManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.net.UnknownHostException
import kotlin.reflect.KClass

abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    protected var binding: ViewDataBinding? = null

    protected abstract val viewModelClass: KClass<T>

    protected val viewModel: T by lazy { getViewModel(viewModelClass) }

    protected open var progressBarId: Int = R.id.progressView

    private val progressView: View? by lazy {
        findViewById<View>(progressBarId)
    }

    protected abstract val layoutId: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frame = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(findColor(R.color.background))
        }
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, frame, true)
        binding?.setVariable(BR.viewModel, viewModel)
        frame.addView(buildMessageView().also { messageView = it })
        setContentView(frame)
        observeAllData()
    }


    private fun observeAllData() {
        observeLiveData()
        observeLoaderAndError()
        observeConnectivityState()
    }

    abstract fun observeLiveData()

    private fun observeLoaderAndError() {
        with(viewModel) {
            loading.observe(
                this@BaseActivity,
                Observer<Boolean> { it?.run { showProgress(this) } })
            errors.observe(this@BaseActivity, Observer { it?.get()?.let { showError(it) } })
        }
    }


    fun showProgress(isShow: Boolean) {
        progressView?.apply { if (isShow) show() else hide() }
    }

    fun showError(message: Throwable) {
        val toastMessage: String = when (message) {
            is UnknownHostException -> getString(R.string.network_error_message)
            else -> "Unknown error"
        }
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
    }


    private val connectedManager: ConnectedManager by inject()
    protected var messageView: TextView? = null

    private fun observeConnectivityState() {
        connectedManager.isConnected.observe(this, Observer { c ->
            messageView?.animate()
                ?.setInterpolator(AccelerateInterpolator())
                ?.let {
                    if (c) {
                        it.translationY(-messageView!!.height.toFloat())
                            ?.alpha(0f)
                            ?.withEndAction {
                                messageView?.hide()
                            }
                    } else {
                        messageView?.alpha = 0f
                        messageView?.show()
                        it.translationY(0f)
                            ?.alpha(1.0f)
                    }
                }
                ?.apply { duration = 300 }
                ?.start()
        })
    }

    private fun buildMessageView() = TextView(this).apply {
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        visibility = View.GONE
        text = getString(R.string.network_error_message)
        textAlignment = View.TEXT_ALIGNMENT_CENTER
        setTextColor(findColor(R.color.onPrimary))
        background = ColorDrawable(findColor(R.color.offline_panel_background))
        val padding = resources.getDimensionPixelSize(R.dimen.margin_4dp)
        setPadding(
            padding,
            padding,
            padding,
            padding
        )
        gravity = Gravity.TOP
        setOnClickListener {
            //onMessageViewClick?.invoke()
        }
    }

}