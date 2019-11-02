package app.melum.common

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("showIf")
    fun showIf(view: View?, show: Boolean) {
        view?.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("imageUrl", "placeholder", "corners", requireAll = false)
    fun imageUrl(imageView: ImageView?, url: String?, placeholderId: Drawable?, corners: Float?) {
        if (url == null) return

        imageView?.run {
            Glide.with(imageView.context)
                .load(if (url.isNotBlank()) url else placeholderId)
                .let {
                    if (corners != null)
                        it.transform(CenterCrop(), RoundedCorners(corners.toInt()))
                    else
                        it
                }
                .let {
                    if (placeholderId != null)
                        it.placeholder(placeholderId)
                    else it
                }
                .into(imageView)
        }
    }


}