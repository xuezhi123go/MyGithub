package com.gkzxhn.mygithub.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.Toolbar
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.gkzxhn.mygithub.di.module.GlideApp

/**
 * Created by 方 on 2017/10/26.
 */

fun Toolbar.loadIcon(context: Context, url: String, errorRes: Int) {
    GlideApp.with(context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(RequestOptions.circleCropTransform())
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    this@loadIcon.setLogo(errorRes)
                    return true
                }

            }).into(object : SimpleTarget<Drawable>() {
        override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
            this@loadIcon.setLogo(resource)
        }
    })
}