package sphe.inews.util

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import sphe.inews.R
import sphe.inews.models.Bookmark


fun Any?.notNull(): Boolean {

    return this != null
}

fun Bookmark?.notNull(): Boolean {

    return this != null
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ViewGroup.viewHolderItemBinding(resId: Int): ViewDataBinding {
    return DataBindingUtil.inflate(
        LayoutInflater.from(this.context),
        resId,
        this,
        false
    )
}

fun String.shrinkString(): String {
    val arrayTitle = this.split("-")
    return if (arrayTitle.size > 1) {
        arrayTitle[0]
    } else {
        this
    }
}

fun isYoutubeInt(string: String): Int {
    return if (string == "Youtube.com") {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun isYoutubeBoolean(string: String): Boolean = (string == "Youtube.com")

@BindingAdapter("imageUrl")
fun loadImage(view: ShapeableImageView, url: String) = Glide.with(view.context)
    .load(Uri.parse(url))
    .placeholder(R.drawable.logo)
    .error(R.drawable.logo)
    .into(view)

@BindingAdapter("imageDrawable")
fun loadImageDrawable(view: ShapeableImageView, imageResource: Int) = Glide.with(view.context)
    .load("")
    .placeholder(imageResource)
    .error(imageResource)
    .into(view)