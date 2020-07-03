package id.candraibra.bukuwarungtest.ui.home.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.api.load
import coil.transform.RoundedCornersTransformation
import id.candraibra.bukuwarungtest.R
import id.candraibra.bukuwarungtest.base.BaseFragment
import id.candraibra.bukuwarungtest.data.model.User
import id.candraibra.bukuwarungtest.databinding.FragmentDetailBinding
import id.candraibra.bukuwarungtest.utils.Status

class DetailUserFragment : BaseFragment<DetailUserViewModel>() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailUserFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        val userId = args.userId
        viewModel.getUserById(userId).observe(
            requireActivity(), Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { response ->
                                response.value?.let { data -> setView(data) }
                            }
                        }
                        Status.ERROR -> {
                            Log.d("HomeFragment", String.format("%s %s", it.message, it.code))
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        )
    }

    private fun setView(data: User) {
        binding.progressBar.visibility = View.GONE
        binding.ivAvatar.load(data.avatar) { transformations(RoundedCornersTransformation(16f)) }
        binding.tvFirstName.text = data.firstName
        binding.tvLastName.text = data.lastName
        binding.tvEmail.text = data.email
        binding.ibBack.setOnClickListener {
            findNavController().navigate(
                R.id.action_detailUserFragment_to_navigation_home,
                null,
                NavOptions.Builder().setPopUpTo(R.id.navigation_home, true).build()
            )
        }
    }

    override fun getViewModel(): Class<DetailUserViewModel> {
        return DetailUserViewModel::class.java
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}