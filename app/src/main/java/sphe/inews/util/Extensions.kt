package sphe.inews.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import sphe.inews.R
import sphe.inews.models.Bookmark

fun Any?.notNull() : Boolean {

    return this != null
}

fun Bookmark?.notNull() : Boolean {

    return this != null
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ViewGroup.viewHolderItemBinding(resId: Int) : ViewDataBinding {
    return DataBindingUtil.inflate(
        LayoutInflater.from(this.context),
        R.layout.item_article,
        this,
        false
    )
}