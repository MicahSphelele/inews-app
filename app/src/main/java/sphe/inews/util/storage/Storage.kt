package sphe.inews.util.storage

interface Storage {

    fun saveStringData(key: String, data: String)

    fun getStringData(key: String, default: String = ""): String
}