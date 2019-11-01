package app.melum.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import app.melum.BR
import app.melum.R
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    abstract val viewModelClass: KClass<T>

    open val viewModel: T by lazy { getViewModel(viewModelClass) }

    protected abstract val layoutId: Int

    protected open var binding: ViewDataBinding? = null

    @IdRes
    protected var progressBarId: Int = R.id.progressView

    private val progressView: View? by lazy {
        find<View>(progressBarId)
    }

    protected val navController : NavController by lazy {
        findNavController()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeAllLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding?.lifecycleOwner = this
        binding?.setVariable(BR.viewModel, viewModel)
        return binding?.root
    }

    protected abstract fun observeLiveData()

    private fun observeAllLiveData() {
        observeLiveData()
        with(viewModel) {
            loading.observe(this@BaseFragment, Observer<Boolean> {
                it?.run {
                    showProgress(this)
                }
            })
            errors.observe(this@BaseFragment, Observer<Event<Throwable>> {
                it?.get()?.run {
                    showMessage(message ?: "unknown error")
                }
            })
        }
    }


    private fun showProgress(loading: Boolean) {
        progressView?.run {
            if (loading) show() else hide()
        }
    }

    private fun showMessage(message: String) {
        binding?.root?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }


}