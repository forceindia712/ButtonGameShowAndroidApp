package com.gameshow.button.presentation.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gameshow.button.presentation.R
import com.gameshow.button.presentation.databinding.FragmentJoinLobbyBinding
import com.gameshow.button.domain.entities.User
import com.gameshow.button.presentation.adapters.LobbyListAdapter
import com.gameshow.button.presentation.callback.BackPressCallback
import com.gameshow.button.presentation.social.SocialViewFragment
import com.gameshow.button.presentation.viewmodel.SocketViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class LobbyJoinFragment : Fragment() {

    val TAG = "LobbyJoinFragment"

    private lateinit var binding: FragmentJoinLobbyBinding
    private val viewModel: SocketViewModel by activityViewModel()

    private lateinit var lobbyListAdapter: LobbyListAdapter
    private var usersList: List<User> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinLobbyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserve()
    }

    private fun initObserve() {
        viewModel.listUser.observe(viewLifecycleOwner) { listUser ->
            usersList = listUser
            lobbyListAdapter.updateList(usersList)
        }

        viewModel.closeLobbyOrGame.observe(viewLifecycleOwner) { closeLobbyOrGame ->
            if (closeLobbyOrGame)
                closeLobby()
        }

        viewModel.checkStartGame.observe(viewLifecycleOwner) { startGame ->
            if (startGame)
                startGame()
        }

        viewModel.kickUser.observe(viewLifecycleOwner) { kickUser ->
            if (kickUser)
                kickFromLobby()
        }
    }

    private fun initCode() {
        binding.codeTextView.text = viewModel.lobbyCode.value
    }

    private fun startGame() {
        findNavController().navigate(R.id.action_lobbyJoinFragment_to_gameFragment)
    }

    private fun initList() {
        lobbyListAdapter = LobbyListAdapter(viewModel)
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.usersRecyclerView.setHasFixedSize(true)
        binding.usersRecyclerView.adapter = lobbyListAdapter
        lobbyListAdapter.updateList(usersList)
    }

    private fun initUI() {
        addFooter()
        initList()
        initCode()

        viewModel.initJoinLobbyFragment()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            backPress()
        }
    }

    private fun backPress() {
        com.gameshow.button.presentation.dialogs.GameLobbyBackPressDialogFragment(
            getString(R.string.leave),
            getString(R.string.lobby),
            backPressCallback()
        )
        .show(parentFragmentManager, TAG)
    }

    private fun backPressCallback(): BackPressCallback {
        val backPressCallback = object : BackPressCallback {
            override fun setPositiveButton() {
                viewModel.leaveLobbyEmit()
                viewModel.resetLobby()
                com.gameshow.button.presentation.dialogs.CloseLobbyDialogFragment()
                    .show(parentFragmentManager, TAG)
                findNavController().navigate(R.id.action_lobbyJoinFragment_to_lobbyFragment)
            }
        }
        return backPressCallback
    }

    private fun closeLobby() {
        viewModel.resetLobby()
        com.gameshow.button.presentation.dialogs.CloseLobbyDialogFragment()
            .show(parentFragmentManager, TAG)
        findNavController().navigate(R.id.action_lobbyJoinFragment_to_lobbyFragment)
    }

    private fun kickFromLobby() {
        viewModel.resetLobby()
        com.gameshow.button.presentation.dialogs.KickLobbyDialogFragment()
            .show(parentFragmentManager, TAG)
        findNavController().navigate(R.id.action_lobbyJoinFragment_to_lobbyFragment)
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
                findNavController().navigate(R.id.action_lobbyJoinFragment_to_lobbyFragment)
            }
        }, 1000)
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeApplication()
        resumeApplication()
    }
}
