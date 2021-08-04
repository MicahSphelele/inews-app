package sphe.inews.util

import android.content.Context
import android.widget.Toast
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