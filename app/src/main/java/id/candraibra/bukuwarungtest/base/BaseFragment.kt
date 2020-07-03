package id.candraibra.bukuwarungtest.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.candraibra.bukuwarungtest.data.local.database.DatabaseBuilder
import id.candraibra.bukuwarungtest.data.local.database.LocalSource
import id.candraibra.bukuwarungtest.data.network.RemoteSource
import id.candraibra.bukuwarungtest.data.network.RetrofitBuilder

abstract class BaseFragment<VM : ViewModel> : Fragment() {
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                RemoteSource(RetrofitBuilder.getApi),
                LocalSource(
                    DatabaseBuilder.getInstance(requireContext())
                )
            )
        ).get(getViewModel())
    }

    abstract fun getViewModel(): Class<VM>
}