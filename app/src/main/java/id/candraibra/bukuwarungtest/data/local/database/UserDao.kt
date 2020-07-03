package id.candraibra.bukuwarungtest.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.candraibra.bukuwarungtest.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Insert
    suspend fun insertAll(users: List<User>)

    @Query("SELECT * FROM user WHERE user.id LIKE :userId")
    suspend fun getUserById(userId: Int): User

    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("DELETE FROM user WHERE user.id = :userId")
    suspend fun deleteUser(userId: Int)

}