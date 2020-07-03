package id.candraibra.bukuwarungtest.data.network

class RemoteSource(private val apiService: ApiService) {

    suspend fun getUserList() = apiService.getUserList()

}