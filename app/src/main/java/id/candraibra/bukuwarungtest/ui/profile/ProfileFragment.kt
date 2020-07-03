package id.candraibra.bukuwarungtest.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.api.load
import id.candraibra.bukuwarungtest.base.BaseFragment
import id.candraibra.bukuwarungtest.data.local.preference.PrefHelper
import id.candraibra.bukuwarungtest.data.local.preference.PrefKey
import id.candraibra.bukuwarungtest.data.model.User
import id.candraibra.bukuwarungtest.databinding.FragmentProfileBinding
import id.candraibra.bukuwarungtest.databinding.LayoutDeleteDialogBinding
import id.candraibra.bukuwarungtest.utils.Status
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment<ProfileViewModel>() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        if (PrefHelper.getBoolean(PrefKey.CREATED)) {
            viewModel.getUserById(PrefHelper.getInt(PrefKey.USER_ID)).observe(
                requireActivity(), Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                resource.data?.let { response ->
                                    response.value?.let { data -> setView(data) }
                                }
                            }
                            Status.ERROR -> {
                                Log.d("ProfileFragment", String.format("%s %s", it.message, it.code))
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                            }
                            Status.LOADING -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            )
        } else {
            emptyData(View.VISIBLE)
            binding.clView.visibility = View.GONE
        }
        binding.iBAdd.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToAddProfileFragment(0)
            findNavController().navigate(action)
        }
    }

    private fun setView(user: User) {
        binding.progressBar.visibility = View.GONE
        binding.ivAvatar.load(user.avatar)
        binding.tvFirstName.text = user.firstName
        binding.tvLastName.text = user.lastName
        binding.tvEmail.text = user.email
        binding.tvAddress.text = user.address
        binding.btnDelete.setOnClickListener { showDialog() }
        binding.btnEdit.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToAddProfileFragment(PrefHelper.getInt(PrefKey.USER_ID))
            findNavController().navigate(action)
        }
        emptyData(View.GONE)
    }

    private fun showDialog() {
        val view = LayoutDeleteDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context).setView(view.root)
        val alertDialog = builder.show()

        view.btnPositive.setOnClickListener {
            alertDialog.dismiss()
        }

        view.btnNegative.setOnClickListener {
            viewModel.deleteUserById(PrefHelper.getInt(PrefKey.USER_ID)).observe(
                requireActivity(), Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                binding.progressBar.visibility = View.GONE
                                PrefHelper.clearAllPreferences()
                                alertDialog.dismiss()
                                emptyData(View.VISIBLE)
                                binding.clView.visibility = View.GONE
                            }
                            Status.ERROR -> {
                                Log.d("ProfileFragment", String.format("%s %s", it.message, it.code))
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
    }

    override fun getViewModel(): Class<ProfileViewModel> {
        return ProfileViewModel::class.java
    }

    private fun emptyData(visibility: Int) {
        clEmpty.visibility = visibility
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}