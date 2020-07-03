package id.candraibra.bukuwarungtest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.candraibra.bukuwarungtest.data.MyRepository
import id.candraibra.bukuwarungtest.utils.ErrorBody.Companion.getErrorMessage
import id.candraibra.bukuwarungtest.utils.Result
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(private val repository: MyRepository) : ViewModel() {

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Result.loading())
        try {
            emit(Result.success(repository.getUsers()))
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val code = throwable.code()
                    val body = throwable.response()?.errorBody()
                    emit(Result.error(getErrorMessage(body), code))
                }
                is IOException -> {
                    emit(Result.error("Network Error", 0))
                }
                else -> {
                    emit(Result.error("Error Occurred!", 0))
                }
            }
        }
    }

}