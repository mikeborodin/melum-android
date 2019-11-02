package app.melum.common

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


inline fun <reified T : View> View.find(@IdRes id: Int): T? = findViewById(id)
inline fun <reified T : View> Activity.find(@IdRes id: Int): T? = findViewById(id)
inline fun <reified T : View> Fragment.find(@IdRes id: Int): T? = view?.findViewById(id) as T?
inline fun <reified T : View> Dialog.find(@IdRes id: Int): T? = findViewById(id)


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide(gone: Boolean = true) {
    visibility = if (gone) View.GONE else View.INVISIBLE
}

fun View.OnClickListener.setClickListeners(vararg views: View) {
    views.forEach { view -> view.setOnClickListener(this) }
}

fun Context.findColor(@ColorRes colorId: Int): Int = ContextCompat.getColor(this, colorId)


fun Activity.hideKeyboard() = currentFocus?.let {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Fragment.hideKeyboard() = activity?.hideKeyboard()
