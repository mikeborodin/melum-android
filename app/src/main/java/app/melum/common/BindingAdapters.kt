package app.melum.common

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("showIf")
    fun showIf(view: View?, show: Boolean) {
        view?.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("imageUrl", "placeholder", "corners", "crop", requireAll = false)
    fun imageUrl(
        imageView: ImageView?,
        url: String?,
        placeholderId: Drawable?,
        corners: Float?,
        crop: Boolean? = false
    ) {
        if (url == null) return
        val transformations = mutableListOf<Transformation<Bitmap>>()

        if (crop == true)
            transformations.add(CenterCrop())
        if (corners != null)
            transformations.add(RoundedCorners(corners.toInt()))


        imageView?.run {
            Glide.with(imageView.context)
                .load(if (url.isNotBlank()) url else placeholderId).let {
                    if (transformations.isNotEmpty())
                        it.transform(MultiTransformation(transformations))
                    else it
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