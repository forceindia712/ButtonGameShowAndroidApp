package com.gameshow.button.presentation.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gameshow.button.presentation.R
import com.gameshow.button.presentation.databinding.FragmentLoginBinding
import com.gameshow.button.domain.entities.Profile
import com.gameshow.button.presentation.adapters.LoginListAdapter
import com.gameshow.button.presentation.callback.DeleteCallbackImpl
import com.gameshow.button.presentation.social.SocialViewFragment
import com.gameshow.button.presentation.viewmodel.ProfileViewModel
import com.gameshow.button.presentation.viewmodel.SocketViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class LoginFragment : Fragment(), LoginListAdapter.ClickListener {

    val TAG = "LoginFragment"

    private lateinit var binding: FragmentLoginBinding
    private lateinit var lobbyListAdapter: LoginListAdapter
    private val profileViewModel: ProfileViewModel by activityViewModel()
    private val socketViewModel: SocketViewModel by activityViewModel()

    private var usersList: List<Profile> = arrayListOf()
    private var closeApp: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initList()
        observeUI()
        initPopup()
    }

    private fun initList() {
        lobbyListAdapter =
            LoginListAdapter(socketViewModel)
        binding.usersListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.usersListRecyclerView.setHasFixedSize(true)
        binding.usersListRecyclerView.adapter = lobbyListAdapter
        lobbyListAdapter.setClickListener(this)
    }

    private fun observeUI() {
        profileViewModel.profileList.observe(viewLifecycleOwner) { profileList ->
            usersList = profileList
            lobbyListAdapter.updateList(profileList)
            binding.createNewAccountButton.isVisible = usersList.size < 3
        }
    }

    private fun initUI() {
        addFooter()
        binding.createNewAccountButton.setOnClickListener {
            if (usersList.size >= 3)
                Toast.makeText(context, R.string.maximum_profiles, Toast.LENGTH_SHORT).show()
            else
                findNavController().navigate(R.id.action_loginFragment_to_createAccountFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (closeApp)
                activity?.finishAffinity()
            else
                refreshVariable()
        }
    }

    private fun initPopup() {
        binding.overflowButton.setOnClickListener {
            val popup = PopupMenu(context, binding.overflowButton)
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.regulations -> {
                        findNavController().navigate(R.id.action_loginFragment_to_rulesPrivacyPolicyFragment)
                        true
                    }

                    R.id.information -> {
                        findNavController().navigate(R.id.action_loginFragment_to_licenseFragment)
                        true
                    }

                    else -> false
                }

            }
            popup.show()
        }
    }

    private fun clickView(number: Int) {
        socketViewModel.setCurrentProfile(usersList[number])
        findNavController().navigate(R.id.action_loginFragment_to_lobbyFragment)
    }

    private fun longClickView(number: Int) {
        com.gameshow.button.presentation.dialogs.ProfileRemoveDialogFragment(
            DeleteCallbackImpl(
                profileViewModel
            ), usersList[number]
        )
            .show(parentFragmentManager, TAG)
    }

    override fun onLongClick(
        item: Profile,
        holder: LoginListAdapter.MyViewHolder,
        position: Int
    ) {
        longClickView(position)
    }

    override fun onItemClick(
        item: Profile,
        holder: LoginListAdapter.MyViewHolder,
        position: Int
    ) {
        clickView(position)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun refreshVariable() {
        closeApp = true
        GlobalScope.launch {
            delay(1000)
            closeApp = false
        }
    }

    private fun resumeApplication() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (socketViewModel.checkCommunication.value == false) {
                socketViewModel.hardResetLobby()
            }
        }, 1000)
    }

    private fun addFooter() {
        childFragmentManager
            .beginTransaction()
            .add(binding.fragmentContainerViewSocial.id, SocialViewFragment())
            .commit()
    }

    override fun onResume() {
        super.onResume()
        socketViewModel.resumeApplication()
        resumeApplication()
    }
}