package sphe.inews.util

import sphe.inews.models.Bookmark

fun Any?.notNull() : Boolean {

    return this != null
}

fun Bookmark?.notNull() : Boolean {

    return this != null
}