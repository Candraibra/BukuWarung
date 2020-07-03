package id.candraibra.bukuwarungtest.ui.profile

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
import id.candraibra.bukuwarungtest.R
import id.candraibra.bukuwarungtest.base.BaseFragment
import id.candraibra.bukuwarungtest.data.local.preference.PrefHelper
import id.candraibra.bukuwarungtest.data.local.preference.PrefKey
import id.candraibra.bukuwarungtest.data.model.User
import id.candraibra.bukuwarungtest.databinding.FragmentAddProfileBinding
import id.candraibra.bukuwarungtest.utils.Status


class AddProfileFragment : BaseFragment<ProfileViewModel>() {
    private var _binding: FragmentAddProfileBinding? = null
    private val binding get() = _binding!!
    private val args: AddProfileFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (args.userId == 0) {
            binding.btnSave.setOnClickListener {
                if (fillAll()) {
                    val userId = 8
                    val photo = binding.edtUserAvatar.text.toString()
                    val firstName = binding.edtFirstName.text.toString()
                    val lastName = binding.edtLastName.text.toString()
                    val email = binding.edtEmail.text.toString()
                    val address = binding.edtAddress.text.toString()
                    val user = User(userId, photo, email, firstName, lastName, address)
                    insertData(user)
                }
            }
        } else {
            viewModel.getUserById(args.userId).observe(
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
                            }
                        }
                    }
                }
            )
            binding.btnSave.setOnClickListener {
                if (fillAll()) {
                    val userId = PrefHelper.getInt(PrefKey.USER_ID)
                    val photo = binding.edtUserAvatar.text.toString()
                    val firstName = binding.edtFirstName.text.toString()
                    val lastName = binding.edtLastName.text.toString()
                    val email = binding.edtEmail.text.toString()
                    val address = binding.edtAddress.text.toString()
                    val user = User(userId, photo, email, firstName, lastName, address)
                    updateData(user)
                }
            }
        }
    }

    private fun setView(data: User) {
        binding.edtUserAvatar.setText(data.avatar)
        binding.edtAddress.setText(data.address)
        binding.edtEmail.setText(data.email)
        binding.edtFirstName.setText(data.firstName)
        binding.edtLastName.setText(data.lastName)
    }

    private fun insertData(user: User) {
        viewModel.insertUser(user).observe(
            requireActivity(), Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(requireContext(), getString(R.string.succes_create), Toast.LENGTH_LONG).show()
                            PrefHelper.setBoolean(PrefKey.CREATED, true)
                            PrefHelper.setInt(PrefKey.USER_ID, user.id)
                            findNavController().navigate(
                                R.id.action_addProfileFragment_to_navigation_profile,
                                null,
                                NavOptions.Builder().setPopUpTo(R.id.navigation_profile, true).build()
                            )
                        }
                        Status.ERROR -> {
                            Log.d("AddProfileFragment", String.format("%s %s", it.message, it.code))
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                        }
                    }
                }
            }
        )
    }

    private fun updateData(user: User) {
        viewModel.updateUser(user).observe(
            requireActivity(), Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(requireContext(), getString(R.string.sucess_update), Toast.LENGTH_LONG).show()
                            PrefHelper.setBoolean(PrefKey.CREATED, true)
                            PrefHelper.setInt(PrefKey.USER_ID, user.id)
                            findNavController().navigate(
                                R.id.action_addProfileFragment_to_navigation_profile,
                                null,
                                NavOptions.Builder().setPopUpTo(R.id.navigation_profile, true).build()
                            )
                        }
                        Status.ERROR -> {
                            Log.d("AddProfileFragment", String.format("%s %s", it.message, it.code))
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                        }
                    }
                }
            }
        )
    }

    private fun fillAll(): Boolean {
        return if (binding.edtUserAvatar.text.isNotEmpty() &&
            binding.edtFirstName.text.isNotEmpty() &&
            binding.edtLastName.text.isNotEmpty() &&
            binding.edtEmail.text.isNotEmpty() &&
            binding.edtAddress.text.isNotEmpty()
        ) {
            true
        } else {
            Toast.makeText(requireContext(), getString(R.string.must_filled), Toast.LENGTH_LONG).show()
            false
        }
    }

    override fun getViewModel(): Class<ProfileViewModel> {
        return ProfileViewModel::class.java
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}