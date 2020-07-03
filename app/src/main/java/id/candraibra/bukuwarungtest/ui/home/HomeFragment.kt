package id.candraibra.bukuwarungtest.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import id.candraibra.bukuwarungtest.base.BaseFragment
import id.candraibra.bukuwarungtest.data.model.User
import id.candraibra.bukuwarungtest.databinding.FragmentHomeBinding
import id.candraibra.bukuwarungtest.utils.Status

class HomeFragment : BaseFragment<HomeViewModel>() {
    private lateinit var adapter: HomeAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    private fun initView() {
        viewModel.getUsers().observe(
            requireActivity(), Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { response ->
                                response.value?.let { list -> initAdapter(list) }
                            }
                        }
                        Status.ERROR -> {
                            Log.d("HomeFragment", String.format("%s %s", it.message, it.code))
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.rvHome.visibility = View.GONE
                        }
                    }
                }
            }
        )
    }

    private fun initAdapter(list: List<User>) {
        adapter = HomeAdapter(list)
        adapter.notifyDataSetChanged()
        binding.rvHome.adapter = adapter
        binding.progressBar.visibility = View.GONE
        binding.rvHome.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}