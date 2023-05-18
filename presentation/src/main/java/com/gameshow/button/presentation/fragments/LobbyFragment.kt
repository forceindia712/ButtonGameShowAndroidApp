package com.gameshow.button.presentation.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gameshow.button.presentation.R
import com.gameshow.button.presentation.databinding.FragmentLobbyBinding
import com.gameshow.button.presentation.social.SocialViewFragment
import com.gameshow.button.presentation.viewmodel.SocketViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class LobbyFragment : Fragment() {

    val TAG = "LobbyFragment"

    private lateinit var binding: FragmentLobbyBinding
    private val viewModel: SocketViewModel by activityViewModel()

    private var currentCheckCommunication: Boolean = false
    private var checkConnection: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLobbyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserve()
    }

    private fun initObserve() {
        viewModel.checkCommunication.observe(viewLifecycleOwner) { communication ->
            checkConnection = communication
        }
    }

    private fun initUI() {
        addFooter()
        currentCheckCommunication = false
        viewModel.checkCommunicationOn()

        binding.loadingServerProgressBar.isVisible = false

        binding.createLobbyButton.setOnClickListener {
            if (!currentCheckCommunication) {
                currentCheckCommunication = true
                if (checkConnection)
                    createLobby()
                else {
                    communicationFalse("create")
                }
            }
        }

        binding.enterToLobbyButton.setOnClickListener {
            if (!currentCheckCommunication) {
                currentCheckCommunication = true
                if (checkConnection)
                    joinToLobby()
                else {
                    communicationFalse("join")
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            backPress()
        }
    }

    private fun backPress() {
        findNavController().navigate(R.id.action_lobbyFragment_to_loginFragment)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun communicationFalse(createJoinLobby: String) {
        binding.loadingServerProgressBar.isVisible = true
        viewModel.initSocket()
        viewModel.checkCommunicationEmit()
        Handler(Looper.getMainLooper()).postDelayed({
                if (!checkConnection) {
                    Toast.makeText(context, R.string.server_communication_error, Toast.LENGTH_SHORT)
                        .show()
                    binding.loadingServerProgressBar.isVisible = false
                    currentCheckCommunication = false
                } else {
                    binding.loadingServerProgressBar.isVisible = false
                    currentCheckCommunication = false
                    when (createJoinLobby) {
                        "create" -> {
                            createLobby()
                        }

                        "join" -> {
                            joinToLobby()
                        }
                    }
                }
        }, 3000)
    }

    private fun createLobby() {
        currentCheckCommunication = false
        findNavController().navigate(R.id.action_lobbyFragment_to_createLobbyFragment)
    }

    private fun joinToLobby() {
        currentCheckCommunication = false
        findNavController().navigate(R.id.action_lobbyFragment_to_choiceLobbyFragment)
    }

    private fun addFooter() {
        childFragmentManager
            .beginTransaction()
            .add(binding.fragmentContainerViewSocial.id, SocialViewFragment())
            .commit()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun resumeApplication() {
        GlobalScope.launch {
            delay(1000)
            if (viewModel.checkCommunication.value == false) {
                viewModel.hardResetLobby()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeApplication()
        if (!viewModel.currentProfile.value?.nickname.isNullOrEmpty())
            resumeApplication()
        else {
            viewModel.hardResetLobby()
            findNavController().navigate(R.id.action_lobbyFragment_to_loginFragment)
        }


    }
}