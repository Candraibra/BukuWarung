package id.candraibra.bukuwarungtest.utils

import id.candraibra.bukuwarungtest.utils.Status.*


data class Result<out T>(val status: Status, val data: T?, val message: String?, val code: Int?) {
    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(SUCCESS, data, null, null)
        }

        fun <T> error(message: String, code: Int?): Result<T> {
            return Result(ERROR, null, message, code)
        }

        fun loading(): Result<Nothing> {
            return Result(LOADING, null, null, null)
        }
    }
}