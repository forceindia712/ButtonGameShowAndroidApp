package com.gameshow.button.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gameshow.button.presentation.R
import com.gameshow.button.presentation.databinding.FragmentLicenseBinding
import com.gameshow.button.presentation.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class LicenseFragment : Fragment() {

    val TAG = "LicenseFragment"

    private lateinit var binding: FragmentLicenseBinding
    private val viewModel: ProfileViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLicenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.avatarsView.setOnClickListener {
            startActivity(openActivity("https://github.com/multiavatar/Multiavatar/blob/main/LICENSE"))
        }

        binding.applicationLogoView.setOnClickListener {
            startActivity(openActivity("https://github.com/forceindia712/ButtonGame/blob/master/previews/license_taphere.pdf"))
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_licenseFragment_to_loginFragment)
        }
    }

    private fun openActivity(pageName: String): Intent {
        val openApplicationIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(pageName)
            if (requireContext().packageManager.getLaunchIntentForPackage("com.github.android") != null)
                setPackage("com.github.android")
        }
        return openApplicationIntent
    }

}