package app.melum.common

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("showIf")
    fun showIf(view: View?, show: Boolean) {
        view?.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("imageUrl", "placeholder")
    fun imageUrl(imageView: ImageView?, url: String?, placeholderId: Drawable?) {
        if (url == null) return

        imageView?.run {
            Glide.with(imageView.context)
                .load(if (url.isNotBlank()) url else placeholderId)
                .let {
                    if (placeholderId != null)
                        it.placeholder(placeholderId)
                    else it
                }
                .into(imageView)
        }
    }


}