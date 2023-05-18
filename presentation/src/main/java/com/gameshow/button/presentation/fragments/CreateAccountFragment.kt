package com.gameshow.button.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gameshow.button.presentation.R
import com.gameshow.button.presentation.databinding.FragmentCreateAccountBinding
import com.gameshow.button.domain.entities.Profile
import com.gameshow.button.presentation.callback.BackPressCallback
import com.gameshow.button.presentation.social.SocialViewFragment
import com.gameshow.button.presentation.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CreateAccountFragment : Fragment() {

    val TAG = "CreateAccountFragment"

    private lateinit var binding: FragmentCreateAccountBinding
    private val profileViewModel: ProfileViewModel by activityViewModel()
    private var avatarName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        addFooter()
        renewIcon()

        binding.avatarRenewIcon.setOnClickListener {
            renewIcon()
        }

        binding.avatarImageView.setOnClickListener {
            renewIcon()
        }

        binding.createNewAccountButton.setOnClickListener {
            if (binding.userNameEditText.length() == 0 ||
                binding.userNameEditText.equals("Name")
            )
                binding.userNameEditText.error = getString(R.string.field_is_blank)
            else {
                val tempProfile = Profile(
                    binding.userNameEditText.text.toString(),
                    avatarName
                )
                profileViewModel.saveProfile(tempProfile)
                findNavController().navigate(R.id.action_createAccountFragment_to_loginFragment)
            }
        }

        binding.onBackPressedArrow.setOnClickListener {
            backPress()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            backPress()
        }
    }

    private fun backPressCallback(): BackPressCallback {
        val backPressCallback = object : BackPressCallback {
            override fun setPositiveButton() {
                findNavController().navigate(R.id.action_createAccountFragment_to_loginFragment)
            }
        }
        return backPressCallback
    }

    private fun backPress() {
        if (isEmpty())
            findNavController().navigate(R.id.action_createAccountFragment_to_loginFragment)
        else {
            val dialog = com.gameshow.button.presentation.dialogs.ProfileBackPressDialogFragment(
                backPressCallback()
            )
            dialog.show(parentFragmentManager, TAG)
        }
    }

    private fun isEmpty(): Boolean {
        return binding.userNameEditText.length() == 0 ||
                binding.userNameEditText.equals("Name")
    }

    private fun renewIcon() {
        avatarName = profileViewModel.renewIcon()
        val resourceId = profileViewModel.getAvatar(avatarName)
        if (resourceId != null)
            binding.avatarImageView.setImageResource(resourceId)
    }

    private fun addFooter() {
        childFragmentManager
            .beginTransaction()
            .add(binding.fragmentContainerViewSocial.id, SocialViewFragment())
            .commit()
    }
}