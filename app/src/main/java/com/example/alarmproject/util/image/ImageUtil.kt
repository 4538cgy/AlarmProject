package com.example.alarmproject.util.image

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import java.security.AccessController.getContext

class ImageUtil {
    companion object {
        @BindingAdapter(value = ["imageUrlWithRadius", "radius", "isFitCenter"], requireAll = false)
        @JvmStatic
        fun loadImageWithRadius(view: ImageView, url: String?, radius: Float, isFitCenter: Boolean = false) {
            val iRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, view.context.resources.displayMetrics).toInt()
            val option = RequestOptions()
            val scaleType = if (isFitCenter) FitCenter() else CenterCrop()
            Glide.with(view.context)
                .load(url)
                .transform(scaleType, RoundedCorners(iRadius))
                .apply(option)
                .into(view)
        }

    }
}