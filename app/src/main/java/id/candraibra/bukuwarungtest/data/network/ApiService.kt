package id.candraibra.bukuwarungtest.data.network

import id.candraibra.bukuwarungtest.data.model.UserResponse
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUserList(): UserResponse
}