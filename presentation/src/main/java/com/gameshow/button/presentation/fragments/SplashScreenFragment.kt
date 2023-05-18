package com.gameshow.button.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gameshow.button.presentation.R
import com.gameshow.button.presentation.databinding.FragmentSplashScreenBinding
import com.gameshow.button.presentation.dialogs.InternetNotConnectionDialogFragment
import com.gameshow.button.presentation.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    val TAG = "SplashScreenFragment"

    private lateinit var binding: FragmentSplashScreenBinding
    private var canYouClick = true
    private var checkInternetConnection = true
    private val viewModel: ProfileViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        canYouClick = true
        binding.progressBar.isVisible = false
        checkInternetConnection = viewModel.checkInternetConnection()
        loadingApp()

        binding.splashscreenIconView.setOnClickListener {
            if (canYouClick) {
                checkInternetConnection = viewModel.checkInternetConnection()
                loadingApp()
            }
        }
    }

    private fun loadingApp() {
        if (canYouClick) {
            canYouClick = false
            Handler(Looper.getMainLooper()).postDelayed({
                if (checkInternetConnection) {
                    if (viewModel.acceptRules.value == true)
                        findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
                    else
                        findNavController().navigate(R.id.action_splashScreenFragment_to_rulesPrivacyPolicyFragment)
                } else {
                    binding.progressBar.isVisible = true
                    checkInternetConnection = viewModel.checkInternetConnection()

                    Handler(Looper.getMainLooper()).postDelayed({
                        if (checkInternetConnection) {
                            if (viewModel.acceptRules.value == true)
                                findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
                            else
                                findNavController().navigate(R.id.action_splashScreenFragment_to_rulesPrivacyPolicyFragment)
                        } else {
                            binding.progressBar.isVisible = false
                            InternetNotConnectionDialogFragment()
                                .show(parentFragmentManager, TAG)
                            canYouClick = true
                        }
                    }, 2000)
                }
            }, 2000)
        }
    }

}