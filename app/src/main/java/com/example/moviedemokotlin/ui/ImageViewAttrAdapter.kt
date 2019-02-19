package com.example.moviedemokotlin.ui

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * Author: created by MarkYoung on 22/01/2019 12:23
 */
class ImageViewAttrAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl","placeHolder","errorImage")
        fun loadImage(imageView: ImageView, url: String?, holdDrawable: Drawable, errorDrawable: Drawable){

            Glide.with(imageView.context)
                    .load("https://image.tmdb.org/t/p/w200" + url)
                    .apply(RequestOptions().placeholder(holdDrawable))
                    .apply(RequestOptions().error(errorDrawable))
                    .apply(RequestOptions().centerCrop().transforms(CenterCrop(), RoundedCorners(15)))
                    .apply(RequestOptions().override(200,200))
                    .into(imageView)
        }
    }
}