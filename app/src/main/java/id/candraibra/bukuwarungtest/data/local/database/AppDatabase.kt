package id.candraibra.bukuwarungtest.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.candraibra.bukuwarungtest.data.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}