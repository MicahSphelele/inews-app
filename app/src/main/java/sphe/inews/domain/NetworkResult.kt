package sphe.inews.domain

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null) {

    open class Success<T>(data: T) : NetworkResult<T>(data)
    open class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)
    open class Loading<T> : NetworkResult<T>()

}
