package sphe.inews.domain.models.storage

interface Storage {

    fun saveStringData(key: String, data: String)

    fun getStringData(key: String, default: String = ""): String

    fun saveIntData(key: String, data: Int)

    fun getIntData(key: String): Int

}