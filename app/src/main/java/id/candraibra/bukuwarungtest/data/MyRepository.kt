package id.candraibra.bukuwarungtest.data

import androidx.lifecycle.MutableLiveData
import id.candraibra.bukuwarungtest.data.local.database.LocalSource
import id.candraibra.bukuwarungtest.data.model.User
import id.candraibra.bukuwarungtest.data.network.RemoteSource


class MyRepository(private val remoteSource: RemoteSource, private val dbSource: LocalSource) {

    suspend fun getUsers(): MutableLiveData<List<User>> {
        val userResponse = MutableLiveData<List<User>>()
        val users = dbSource.getAll()
        if (users.isEmpty()) {
            val response = remoteSource.getUserList()
            val usersFromApi = response.data
            val usersToInsertInDB = mutableListOf<User>()
            for (apiUser in usersFromApi) {
                val user = User(apiUser.id, apiUser.avatar, apiUser.email, apiUser.firstName, apiUser.lastName)
                usersToInsertInDB.add(user)
            }
            dbSource.insertAll(usersToInsertInDB)
            userResponse.postValue(usersToInsertInDB)
        } else {
            userResponse.postValue(users)
        }
        return userResponse
    }

    suspend fun getUserById(userId: Int): MutableLiveData<User> {
        val userResponse = MutableLiveData<User>()
        val users = dbSource.getUserById(userId)
        userResponse.postValue(users)
        return userResponse
    }

    suspend fun insertUser(user: User) {
        dbSource.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        dbSource.updateUser(user)
    }

    suspend fun deleteUser(userId: Int) {
        dbSource.deleteUser(userId)
    }

}