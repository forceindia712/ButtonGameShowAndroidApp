package com.gameshow.button.presentation.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gameshow.button.presentation.R
import com.gameshow.button.presentation.databinding.FragmentChoiceLobbyBinding
import com.gameshow.button.presentation.social.SocialViewFragment
import com.gameshow.button.presentation.utils.EnterCodeValidator
import com.gameshow.button.presentation.viewmodel.SocketViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ChoiceLobbyFragment : Fragment() {

    val TAG = "ChoiceLobbyFragment"

    private lateinit var binding: FragmentChoiceLobbyBinding
    private val viewModel: SocketViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChoiceLobbyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserve()
    }

    private fun initObserve() {
        viewModel.joinToLobbyBoolean.observe(viewLifecycleOwner) { joinToLobby ->
            if (joinToLobby)
                joinToLobby()
        }

        viewModel.gameStarted.observe(viewLifecycleOwner) { gameStarted ->
            if (gameStarted)
                Toast.makeText(context, R.string.unable_to_join_lobby, Toast.LENGTH_SHORT).show()
        }

        viewModel.wrongLobbyCode.observe(viewLifecycleOwner) { wrongLobbyCode ->
            if (wrongLobbyCode)
                Toast.makeText(context, R.string.code_is_invalid, Toast.LENGTH_SHORT).show()
        }
    }



    private fun initUI() {
        addFooter()
        binding.enterCodeEditText.filters = arrayOf(EnterCodeValidator.enterCodeValidator)

        viewModel.initChoiceLobbyFragment()

        binding.joinLobbyButton.setOnClickListener {
            if (binding.enterCodeEditText.length() != 5)
                binding.enterCodeEditText.error = getString(R.string.code_max_5_character)
            else {
                viewModel.joinToLobbyBooleanEmit(binding.enterCodeEditText.text.toString())
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_choiceLobbyFragment_to_lobbyFragment)
        }
    }

    private fun joinToLobby() {
        findNavController().navigate(R.id.action_choiceLobbyFragment_to_lobbyJoinFragment)
    }

    private fun addFooter() {
        childFragmentManager
            .beginTransaction()
            .add(binding.fragmentContainerViewSocial.id, SocialViewFragment())
            .commit()
    }

    private fun resumeApplication() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (viewModel.checkCommunication.value == false) {
                viewModel.hardResetLobby()
                findNavController().navigate(R.id.action_choiceLobbyFragment_to_lobbyFragment)
            }
        }, 1000)
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeApplication()
        resumeApplication()
    }
}