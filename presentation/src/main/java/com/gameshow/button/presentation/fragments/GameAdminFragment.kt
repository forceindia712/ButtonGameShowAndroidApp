package com.gameshow.button.presentation.fragments

import GameListAdapter
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gameshow.button.presentation.R
import com.gameshow.button.presentation.databinding.FragmentAdminGameBinding
import com.gameshow.button.domain.entities.User
import com.gameshow.button.presentation.callback.BackPressCallback
import com.gameshow.button.presentation.callback.DeleteUserFromLobbyCallback
import com.gameshow.button.presentation.viewmodel.SocketViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class GameAdminFragment : Fragment(), GameListAdapter.ClickListener {

    val TAG = "GameAdminFragment"

    private lateinit var binding: FragmentAdminGameBinding
    private val viewModel: SocketViewModel by activityViewModel()

    private lateinit var gameListAdapter: GameListAdapter
    private var usersList: List<User> = arrayListOf()
    private var clickNumbers: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserve()
    }

    private fun initObserve() {
        viewModel.listUser.observe(viewLifecycleOwner) { gameList ->
            gameListAdapter.updateList(gameList)
        }

        viewModel.closeLobbyOrGame.observe(viewLifecycleOwner) { closeLobbyOrGame ->
            if (closeLobbyOrGame)
                closeGame()
        }

        viewModel.startRound.observe(viewLifecycleOwner) { startRound ->
            if (startRound) {
                clickNumbers = 0
            }
        }
    }

    private fun initUI() {
        clickNumbers = 0
        usersList = viewModel.listUser.value ?: arrayListOf()
        initList()

        viewModel.initGameAdminFragment()

        binding.gameButton.setOnClickListener {
            if (clickNumbers < 1) {
                viewModel.buttonClickedEmit()
                clickNumbers++
            } else if (clickNumbers < 3) {
                Toast.makeText(context, R.string.do_not_press_again, Toast.LENGTH_SHORT).show()
                clickNumbers++
            }
        }

        binding.startRoundButton.setOnClickListener {
            viewModel.startRoundEmit()
        }

        binding.closeLobbyButton.setOnClickListener {
            backPress()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            backPress()
        }
    }

    private fun initList() {
        gameListAdapter = GameListAdapter(viewModel)
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.usersRecyclerView.setHasFixedSize(true)
        binding.usersRecyclerView.adapter = gameListAdapter
        gameListAdapter.setClickListener(this)
        gameListAdapter.updateList(usersList)
    }

    private fun backPress() {
        com.gameshow.button.presentation.dialogs.GameLobbyBackPressDialogFragment(
            getString(R.string.close),
            getString(R.string.game),
            backPressCallback()
        ).show(parentFragmentManager, TAG)
    }

    private fun backPressCallback(): BackPressCallback {
        val backPressCallback = object : BackPressCallback {
            override fun setPositiveButton() {
                viewModel.closeGameEmit()
                viewModel.resetLobby()
                findNavController().navigate(R.id.action_gameAdminFragment_to_lobbyFragment)
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

    private fun closeGame() {
        viewModel.resetLobby()
        com.gameshow.button.presentation.dialogs.CloseLobbyDialogFragment()
            .show(parentFragmentManager, TAG)
        findNavController().navigate(R.id.action_gameAdminFragment_to_lobbyFragment)
    }

    override fun onLongClick(item: User, holder: RecyclerView.ViewHolder, position: Int) {
        if (!item.isAdmin)
            deleteUser(item)
    }

    private fun resumeApplication() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (viewModel.checkCommunication.value == false) {
                viewModel.hardResetLobby()
                findNavController().navigate(R.id.action_gameAdminFragment_to_lobbyFragment)
            }
        }, 1000)
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeApplication()
        resumeApplication()
    }
}