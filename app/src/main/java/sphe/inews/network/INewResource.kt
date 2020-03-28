package sphe.inews.network

import androidx.annotation.NonNull
import androidx.annotation.Nullable

data class INewResource<T>(val status: Status,  val data: T?, val message: String?) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(@Nullable data:T):INewResource<T> {
            return INewResource(Status.SUCCESS, data, null)
        }
        fun <T> error(@NonNull msg:String, @Nullable data:T?):INewResource<T> {
            return INewResource<T>(Status.ERROR, data, msg)
        }
        fun <T> loading(@Nullable data:T):INewResource<T> {
            return INewResource<T>(Status.LOADING, data, null)
        }
    }
}