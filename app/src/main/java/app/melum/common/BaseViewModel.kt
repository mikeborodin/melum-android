package app.melum.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope, KoinComponent {

    private val _loading: MediatorLiveData<Boolean> = MediatorLiveData()
    private val _errors: MutableLiveData<Event<Throwable>> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading
    val errors: LiveData<Event<Throwable>> = _errors


    private var job: Job = SupervisorJob()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, t ->
        _errors.postValue(Event(t))
        _loading.postValue(false)
    }

    override val coroutineContext: CoroutineContext =
            Dispatchers.Main + job + coroutineExceptionHandler

    private val ioCoroutineContext: CoroutineContext = Dispatchers.IO + job

    protected fun <T> launchHandled(
            task: suspend CoroutineScope.() -> T,
            onSuccess: (T) -> Unit,
            onError: ((Exception) -> Unit)? = null
    ) {
        launch(ioCoroutineContext) {
            _loading.postValue(true)
            try {
                onSuccess(task())
            } catch (e: Exception) {
                onError?.invoke(e)
                _errors.postValue(Event(e))
            } finally {
                _loading.postValue(false)
            }
        }
    }

    override fun onCleared() {
        job.cancel()
        ioCoroutineContext.cancelChildren()
        coroutineContext.cancelChildren()
        super.onCleared()
    }
}