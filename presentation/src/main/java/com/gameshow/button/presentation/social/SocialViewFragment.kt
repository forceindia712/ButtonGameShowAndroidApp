package com.gameshow.button.presentation.social

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gameshow.button.presentation.databinding.ViewSocialBinding
import com.gameshow.button.presentation.utils.SocialObject

class SocialViewFragment : Fragment() {

    val TAG = "SocialViewFragment"

    private lateinit var binding: ViewSocialBinding
    private lateinit var currentlyOpen: SocialEnumClass
    private var goToInstal = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewSocialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.githubForceindiaButtonView.setOnClickListener {
            currentlyOpen = SocialEnumClass.GithubForceIndia
            socialOpen()
        }
        
        binding.githubDawiczkuButtonView.setOnClickListener {
            currentlyOpen = SocialEnumClass.GithubDawiczku
            socialOpen()
        }
    }

    private fun socialOpen() {
        if (requireContext().packageManager.getLaunchIntentForPackage(currentlyOpen.applicationPackage) != null)
            startActivity(openActivity())
        else if (requireContext().packageManager.getLaunchIntentForPackage(SocialObject.googlePlayPackage) != null)
            openDialog()
        else
            startActivity(openActivity())
    }

    private fun openDialog() {
        val dialog = SocialDialogFragment(openCallback())
        dialog.show(parentFragmentManager, TAG)
    }

    private fun openActivity(): Intent {
        val openAplicationIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(currentlyOpen.socialName)
            if (requireContext().packageManager.getLaunchIntentForPackage(currentlyOpen.applicationPackage) != null)
                setPackage(currentlyOpen.applicationPackage)
            }
        return openAplicationIntent
    }

    private fun openCallback(): SocialCallback {
        val callback = object : SocialCallback {
            override fun setPositiveButton() {
                val googlePlayToIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(currentlyOpen.downloadGooglePlay)
                    setPackage(SocialObject.googlePlayPackage)
                }
                goToInstal = true
                startActivity(googlePlayToIntent)
            }

            override fun setNegativeButton() {
                startActivity(openActivity())
            }
        }
        return callback
    }
}