package id.candraibra.bukuwarungtest.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.candraibra.bukuwarungtest.data.MyRepository
import id.candraibra.bukuwarungtest.data.local.database.LocalSource
import id.candraibra.bukuwarungtest.data.network.RemoteSource
import id.candraibra.bukuwarungtest.ui.home.HomeViewModel
import id.candraibra.bukuwarungtest.ui.home.detail.DetailUserViewModel
import id.candraibra.bukuwarungtest.ui.profile.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val remoteSource: RemoteSource, private val dbSource: LocalSource) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(MyRepository(remoteSource, dbSource)) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(MyRepository(remoteSource, dbSource)) as T
            }
            modelClass.isAssignableFrom(DetailUserViewModel::class.java) -> {
                DetailUserViewModel(MyRepository(remoteSource, dbSource)) as T
            }
            else -> throw IllegalArgumentException("Unknown class name")
        }
    }

}

