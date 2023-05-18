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
import androidx.recyclerview.widget.RecyclerView
import com.gameshow.button.presentation.R
import com.gameshow.button.presentation.databinding.FragmentCreateLobbyBinding
import com.gameshow.button.domain.entities.User
import com.gameshow.button.presentation.adapters.LobbyListAdapter
import com.gameshow.button.presentation.callback.BackPressCallback
import com.gameshow.button.presentation.callback.DeleteUserFromLobbyCallback
import com.gameshow.button.presentation.social.SocialViewFragment
import com.gameshow.button.presentation.viewmodel.SocketViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class LobbyCreateFragment : Fragment(), LobbyListAdapter.ClickListener {

    val TAG = "LobbyCreateFragment"

    private lateinit var binding: FragmentCreateLobbyBinding
    private val viewModel: SocketViewModel by activityViewModel()

    private lateinit var lobbyListAdapter: LobbyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateLobbyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserve()
        initList()
    }

    private fun initObserve() {
        viewModel.lobbyCode.observe(viewLifecycleOwner) { lobbyCode ->
            if (!lobbyCode.isNullOrEmpty())
                initCode(lobbyCode)
        }

        viewModel.listUser.observe(viewLifecycleOwner) { listUser ->
            lobbyListAdapter.updateList(listUser)
        }

        viewModel.closeLobbyOrGame.observe(viewLifecycleOwner) { closeLobbyOrGame ->
            if (closeLobbyOrGame)
                closeLobby()
        }

        viewModel.checkStartGame.observe(viewLifecycleOwner) { startGame ->
            if (startGame)
                startGame()
        }
    }

    private fun initUI() {
        addFooter()
        viewModel.initCreateLobbyFragment()

        binding.startGameButton.setOnClickListener {
            viewModel.checkStartGameEmit()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            backPress()
        }
    }

    private fun startGame() {
        findNavController().navigate(R.id.action_createLobbyFragment_to_gameAdminFragment)
    }

    private fun initCode(lobbyCode: String) {
        binding.codeEditText.text = lobbyCode
    }

    private fun closeLobby() {
        viewModel.resetLobby()
        com.gameshow.button.presentation.dialogs.CloseLobbyDialogFragment()
            .show(parentFragmentManager, TAG)
        findNavController().navigate(R.id.action_createLobbyFragment_to_lobbyFragment)
    }

    private fun initList() {
        lobbyListAdapter = LobbyListAdapter(viewModel)
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.usersRecyclerView.setHasFixedSize(true)
        binding.usersRecyclerView.adapter = lobbyListAdapter
        lobbyListAdapter.setClickListener(this)
        lobbyListAdapter.updateList(viewModel.listUser.value.orEmpty())
    }

    private fun backPress() {
        com.gameshow.button.presentation.dialogs.GameLobbyBackPressDialogFragment(
            getString(R.string.close),
            getString(R.string.lobby),
            backPressCallback()
        ).show(parentFragmentManager, TAG)
    }

    private fun backPressCallback(): BackPressCallback {
        val backPressCallback = object : BackPressCallback {
            override fun setPositiveButton() {
                viewModel.closeLobbyEmit()
                viewModel.resetLobby()
                com.gameshow.button.presentation.dialogs.CloseLobbyDialogFragment()
                    .show(parentFragmentManager, TAG)
                findNavController().navigate(R.id.action_createLobbyFragment_to_lobbyFragment)
            }
        }
        return backPressCallback
    }

    private fun deleteUser(item: User) {
        com.gameshow.button.presentation.dialogs.RemoveUserFromLobbyDialogFragment(
            item.nickname,
            deleteUserFromLobbyCallback(item)
        )
            .show(parentFragmentManager, TAG)
    }

    private fun deleteUserFromLobbyCallback(item: User): DeleteUserFromLobbyCallback {
        val deleteUserFromLobbyCallback = object : DeleteUserFromLobbyCallback {
            override fun deleteUser() {
                viewModel.kickUserFromLobbyEmit(item)
            }
        }
        return deleteUserFromLobbyCallback
    }

    override fun onLongClick(item: User, holder: RecyclerView.ViewHolder, position: Int) {
        if (!item.isAdmin)
            deleteUser(item)
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
                findNavController().navigate(R.id.action_createLobbyFragment_to_lobbyFragment)
            }
        }, 1000)
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeApplication()
        resumeApplication()
    }
}