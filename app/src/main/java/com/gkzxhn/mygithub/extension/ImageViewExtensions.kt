package com.gkzxhn.mygithub.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import com.gkzxhn.mygithub.di.module.GlideApp
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


fun ImageView.load(context: Context, url: String) {
    GlideApp.with(context)
            .load(url)
            .transition(withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(this)
}

fun ImageView.loadBlur(context: Context, url: String) {
    GlideApp.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
            .into(this)
}

fun ImageView.loadRoundConner(context: Context, url: String) {
    GlideApp.with(context)
            .load(url)
            .transition(withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(16, 0)))
            .into(this)
}

fun ImageView.load(context: Context, url: String, errorRes: Int) {
    GlideApp.with(context)
            .load(url)
            .transition(withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(RequestOptions.circleCropTransform())
            .error(errorRes)
//            .listener(object : RequestListener<Drawable> {
//                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                    return false
//                }
//
//                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
//                    GlideApp.with(context)
//                            .load(errorRes)
//                            .transition(withCrossFade())
//                            .apply(RequestOptions.circleCropTransform())
//                            .into(this@load)
//                    Log.e(javaClass.simpleName, "current thread is ${Thread.currentThread().name} ; exception is ${e!!.message}")
//                    this@load.setImageResource(errorRes)
//                    return false
//                }
//
//            })
            .into(this)
}

fun ImageView.load(context: Context, url: String, errorRes: Int, signature: ObjectKey) {
    GlideApp.with(context)
            .load(url)
            .signature(signature)
            .transition(withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(RequestOptions.circleCropTransform())
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    GlideApp.with(context)
                            .load(errorRes)
                            .transition(withCrossFade())
                            .apply(RequestOptions.circleCropTransform())
                            .into(this@load)
                    return true
                }

            })
            .into(this)
}

fun ImageView.load(context: Context, bytes: ByteArray) {
    GlideApp.with(context)
            .load(bytes)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .transition(withCrossFade())
            .into(this)
}

fun ImageView.load(context: Context, url: String, completeCallback: () -> Unit) {
    GlideApp.with(context)
            .load(url)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .onlyRetrieveFromCache(true)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    completeCallback()
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    completeCallback()
                    return false
                }
            })
            .into(this)
}
fun ImageView.load(context: Context,id:Int){
    GlideApp.with(context)
            .load(id)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(this)

}


