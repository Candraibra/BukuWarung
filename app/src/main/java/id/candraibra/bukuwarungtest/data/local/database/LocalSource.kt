package id.candraibra.bukuwarungtest.data.local.database

import id.candraibra.bukuwarungtest.data.model.User


class LocalSource(private val appDatabase: AppDatabase) : UserDao {

    override suspend fun getAll(): List<User> = appDatabase.userDao().getAll()

    override suspend fun insertAll(users: List<User>) = appDatabase.userDao().insertAll(users)

    override suspend fun getUserById(userId: Int) = appDatabase.userDao().getUserById(userId)

    override suspend fun insertUser(user: User) = appDatabase.userDao().insertUser(user)

    override suspend fun updateUser(user: User) = appDatabase.userDao().updateUser(user)

    override suspend fun deleteUser(userId: Int) = appDatabase.userDao().deleteUser(userId)

}