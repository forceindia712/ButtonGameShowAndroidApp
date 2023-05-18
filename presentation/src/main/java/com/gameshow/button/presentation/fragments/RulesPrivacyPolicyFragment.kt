package com.gameshow.button.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gameshow.button.presentation.R
import com.gameshow.button.presentation.databinding.FragmentRulesPrivacyPolicyBinding
import com.gameshow.button.presentation.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class RulesPrivacyPolicyFragment : Fragment() {

    val TAG = "RulesPrivacyPolicyFragment"

    private lateinit var binding: FragmentRulesPrivacyPolicyBinding
    private val viewModel: ProfileViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRulesPrivacyPolicyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        if (viewModel.acceptRules.value == true) {
            binding.acceptRules.isVisible = false
            binding.cancelRules.isVisible = false
        } else {
            binding.acceptRules.isVisible = true
            binding.cancelRules.isVisible = true
        }

        binding.acceptRules.setOnClickListener {
            viewModel.changeRulesVersion()
            findNavController().navigate(R.id.action_rulesPrivacyPolicyFragment_to_loginFragment)
        }

        binding.cancelRules.setOnClickListener {
            activity?.finishAffinity()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (viewModel.acceptRules.value == true)
                findNavController().navigate(R.id.action_rulesPrivacyPolicyFragment_to_loginFragment)
            else
                activity?.finishAffinity()
        }
    }

}