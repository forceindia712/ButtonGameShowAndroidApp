package com.gameshow.button.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameshow.button.domain.entities.Profile
import com.gameshow.button.domain.entities.User
import com.gameshow.button.domain.usecases.profile.GetAvatar
import com.gameshow.button.domain.usecases.server.ButtonClickedEmitUseCase
import com.gameshow.button.domain.usecases.server.CheckConnectionEmitUseCase
import com.gameshow.button.domain.usecases.server.CheckConnectionOnUseCase
import com.gameshow.button.domain.usecases.server.CheckStartGameOnUseCase
import com.gameshow.button.domain.usecases.server.CloseGameEmitUseCase
import com.gameshow.button.domain.usecases.server.CloseGameOnUseCase
import com.gameshow.button.domain.usecases.server.CloseGameTimeoutOnUseCase
import com.gameshow.button.domain.usecases.server.CloseLobbyEmitUseCase
import com.gameshow.button.domain.usecases.server.CloseLobbyOnUseCase
import com.gameshow.button.domain.usecases.server.CloseLobbyTimeoutOnUseCase
import com.gameshow.button.domain.usecases.server.CreateLobbyEmitUseCase
import com.gameshow.button.domain.usecases.server.DisconnectSocketUseCaseImpl
import com.gameshow.button.domain.usecases.server.GameStartedOrTooMuchPeopleOnUseCase
import com.gameshow.button.domain.usecases.server.GetLobbyCodeOnUseCase
import com.gameshow.button.domain.usecases.server.GetLobbyUsersEmitUseCase
import com.gameshow.button.domain.usecases.server.GetLobbyUsersOnUseCase
import com.gameshow.button.domain.usecases.server.InitSocketUseCase
import com.gameshow.button.domain.usecases.server.JoinToLobbyBooleanEmitUseCase
import com.gameshow.button.domain.usecases.server.JoinToLobbyBooleanOnUseCase
import com.gameshow.button.domain.usecases.server.KickUserFromLobbyEmitUseCase
import com.gameshow.button.domain.usecases.server.KickUserFromLobbyOnUseCase
import com.gameshow.button.domain.usecases.server.LeaveGameEmitUseCase
import com.gameshow.button.domain.usecases.server.LeaveLobbyEmitUseCase
import com.gameshow.button.domain.usecases.server.RenewConnectionGameEmitUseCase
import com.gameshow.button.domain.usecases.server.RenewConnectionLobbyEmitUseCase
import com.gameshow.button.domain.usecases.server.StartGameEmitUseCase
import com.gameshow.button.domain.usecases.server.StartRoundEmitUseCase
import com.gameshow.button.domain.usecases.server.WrongLobbyCodeOnUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SocketViewModel(
    private val getAvatar: GetAvatar,
    private val buttonClickedEmitUseCase: ButtonClickedEmitUseCase,
    private val checkConnectionEmitUseCase: CheckConnectionEmitUseCase,
    private val checkConnectionOnUseCase: CheckConnectionOnUseCase,
    private val checkStartGameOnUseCase: CheckStartGameOnUseCase,
    private val closeGameEmitUseCase: CloseGameEmitUseCase,
    private val closeGameOnUseCase: CloseGameOnUseCase,
    private val closeLobbyEmitUseCase: CloseLobbyEmitUseCase,
    private val closeGameTimeoutOnUseCase: CloseGameTimeoutOnUseCase,
    private val closeLobbyOnUseCase: CloseLobbyOnUseCase,
    private val closeLobbyTimeoutOnUseCase: CloseLobbyTimeoutOnUseCase,
    private val createLobbyEmitUseCase: CreateLobbyEmitUseCase,
    private val disconnectSocketUseCaseImpl: DisconnectSocketUseCaseImpl,
    private val gameStartedOrTooMuchPeopleOnUseCase: GameStartedOrTooMuchPeopleOnUseCase,
    private val getLobbyCodeOnUseCase: GetLobbyCodeOnUseCase,
    private val getLobbyUsersEmitUseCase: GetLobbyUsersEmitUseCase,
    private val getLobbyUsersOnUseCase: GetLobbyUsersOnUseCase,
    private val initSocketUseCase: InitSocketUseCase,
    private val joinToLobbyBooleanEmitUseCase: JoinToLobbyBooleanEmitUseCase,
    private val joinToLobbyBooleanOnUseCase: JoinToLobbyBooleanOnUseCase,
    private val kickUserFromLobbyEmitUseCase: KickUserFromLobbyEmitUseCase,
    private val kickUserFromLobbyOnUseCase: KickUserFromLobbyOnUseCase,
    private val leaveGameEmitUseCase: LeaveGameEmitUseCase,
    private val leaveLobbyEmitUseCase: LeaveLobbyEmitUseCase,
    private val renewConnectionGameEmitUseCase: RenewConnectionGameEmitUseCase,
    private val renewConnectionLobbyEmitUseCase: RenewConnectionLobbyEmitUseCase,
    private val startGameEmitUseCase: StartGameEmitUseCase,
    private val startRoundEmitUseCase: StartRoundEmitUseCase,
    private val startRoundOnUseCase: GetLobbyUsersOnUseCase,
    private val wrongLobbyCodeOnUseCase: WrongLobbyCodeOnUseCase
) : ViewModel() {

    private val TAG = "SocketViewModel"

    var currentProfile: MutableLiveData<Profile> = MutableLiveData()
    var checkCommunication: MutableLiveData<Boolean> = MutableLiveData(false)
    var lobbyCode: MutableLiveData<String> = MutableLiveData("")
    var listUser: MutableLiveData<List<User>> = MutableLiveData(arrayListOf())

    // Game Units
    var startRound: MutableLiveData<Boolean> = MutableLiveData(false)
    var joinToLobbyBoolean: MutableLiveData<Boolean> = MutableLiveData(false)
    var gameStarted: MutableLiveData<Boolean> = MutableLiveData(false)
    var wrongLobbyCode: MutableLiveData<Boolean> = MutableLiveData(false)
    var checkStartGame: MutableLiveData<Boolean> = MutableLiveData(false)
    var closeLobbyOrGame: MutableLiveData<Boolean> = MutableLiveData(false)
    var kickUser: MutableLiveData<Boolean> = MutableLiveData(false)
    var emptyProfile: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        initSocket()
    }

    fun socketDisconnect() {
        viewModelScope.launch {
            disconnectSocketUseCaseImpl.invoke()
        }
    }

    fun initSocket() {
        viewModelScope.launch {
            initSocketUseCase.invoke()
        }
    }

    fun setCurrentProfile(profile: Profile) {
        currentProfile.postValue(profile)
    }

    fun checkCommunicationEmit() {
        viewModelScope.launch {
            checkConnectionEmitUseCase.invoke()
        }
    }

    fun checkCommunicationOn() {
        viewModelScope.launch {
            checkConnectionOnUseCase.invoke().collect { checkConnection ->
                if (checkConnection.isSuccess)
                    checkCommunication.postValue(checkConnection.getOrNull())
                else
                    checkCommunication.postValue(false)
            }
        }
    }

    fun createLobbyEmit() {
        viewModelScope.launch {
            if (currentProfile.value != null && !currentProfile.value?.nickname.isNullOrEmpty())
                createLobbyEmitUseCase.invoke(
                    CreateLobbyEmitUseCase.CreateLobbyParam(
                        currentProfile.value ?: Profile()
                    )
                )
            else
                emptyProfile.postValue(true)
        }
    }

    fun getLobbyCodeOn() {
        viewModelScope.launch {
            getLobbyCodeOnUseCase.invoke().collect { lobbyCodeResult ->
                if (lobbyCodeResult.isSuccess) {
                    lobbyCode.postValue(lobbyCodeResult.getOrNull())
                } else {
                    Log.i(TAG, lobbyCodeResult.exceptionOrNull().toString())
                }
            }
        }
    }

    fun getLobbyUsersOn() {
        viewModelScope.launch {
            getLobbyUsersOnUseCase.invoke().collect { usersListResult ->
                if (usersListResult.isSuccess) {
                    listUser.postValue(usersListResult.getOrNull())
                } else {
                    Log.i(TAG, usersListResult.exceptionOrNull().toString())
                }
            }
        }
    }

    fun joinToLobbyBooleanEmit(lobbyCode: String) {
        this.lobbyCode.postValue(lobbyCode)
        viewModelScope.launch {
            if (currentProfile.value != null && !currentProfile.value?.nickname.isNullOrEmpty())
                joinToLobbyBooleanEmitUseCase.invoke(
                    JoinToLobbyBooleanEmitUseCase.JoinToLobbyBooleanParam(
                        currentProfile.value ?: Profile(),
                        lobbyCode
                    )
                )
            else
                emptyProfile.postValue(true)
        }
    }

    fun joinToLobbyBooleanOn() {
        viewModelScope.launch {
            joinToLobbyBooleanOnUseCase.invoke().collect { joinToLobbyBooleanResult ->
                if (joinToLobbyBooleanResult.isSuccess) {
                    joinToLobbyBoolean.postValue(joinToLobbyBooleanResult.getOrNull())
                } else {
                    Log.i(TAG, joinToLobbyBooleanResult.exceptionOrNull().toString())
                }
            }
        }
    }

    fun gameStartedOrTooMuchPeopleOn() {
        viewModelScope.launch {
            gameStartedOrTooMuchPeopleOnUseCase.invoke()
                .collect { gameStartedOrTooMuchPeopleResult ->
                    if (gameStartedOrTooMuchPeopleResult.isSuccess) {
                        gameStarted.postValue(gameStartedOrTooMuchPeopleResult.getOrNull())
                    } else {
                        Log.i(TAG, gameStartedOrTooMuchPeopleResult.exceptionOrNull().toString())
                    }
                }
        }
    }

    fun wrongLobbyCodeOn() {
        viewModelScope.launch {
            wrongLobbyCodeOnUseCase.invoke().collect { wrongLobbyCodeResult ->
                if (wrongLobbyCodeResult.isSuccess) {
                    wrongLobbyCode.postValue(wrongLobbyCodeResult.getOrNull())
                } else {
                    Log.i(TAG, wrongLobbyCodeResult.exceptionOrNull().toString())
                }
            }
        }
    }

    fun getLobbyUsersEmit() {
        viewModelScope.launch {
            if (!lobbyCode.value.isNullOrEmpty())
                getLobbyUsersEmitUseCase.invoke(GetLobbyUsersEmitUseCase.LobbyUsersParam(lobbyCode.value.orEmpty()))
        }
    }

    fun checkStartGameEmit() {
        viewModelScope.launch {
            if (!lobbyCode.value.isNullOrEmpty())
                startGameEmitUseCase.invoke(StartGameEmitUseCase.StartGameParam(lobbyCode.value.orEmpty()))
        }
    }

    fun checkStartGameOn() {
        viewModelScope.launch {
            checkStartGameOnUseCase.invoke().collect { checkStartGameResult ->
                if (checkStartGameResult.isSuccess) {
                    checkStartGame.postValue(checkStartGameResult.getOrNull())
                } else {
                    Log.i(TAG, checkStartGameResult.exceptionOrNull().toString())
                }
            }
        }
    }

    fun startRoundEmit() {
        viewModelScope.launch {
            if (!lobbyCode.value.isNullOrEmpty())
                startRoundEmitUseCase.invoke(StartRoundEmitUseCase.StartRoundParam(lobbyCode.value.orEmpty()))
        }
    }

    fun startRoundGameOn() {
        viewModelScope.launch {
            startRoundOnUseCase.invoke().collect { usersListResult ->
                if (usersListResult.isSuccess) {
                    listUser.postValue(usersListResult.getOrNull())
                    startRound.postValue(true)
                } else {
                    Log.i(TAG, usersListResult.exceptionOrNull().toString())
                }
            }
        }
    }

    fun buttonClickedEmit() {
        viewModelScope.launch {
            if (!lobbyCode.value.isNullOrEmpty())
                buttonClickedEmitUseCase.invoke(
                    ButtonClickedEmitUseCase.ButtonClickedParam(
                        lobbyCode.value.orEmpty()
                    )
                )
        }
    }

    fun leaveLobbyEmit() {
        viewModelScope.launch {
            if (!lobbyCode.value.isNullOrEmpty())
                leaveLobbyEmitUseCase.invoke(LeaveLobbyEmitUseCase.LeaveLobbyParam(lobbyCode.value.orEmpty()))
        }
    }

    fun leaveGameEmit() {
        viewModelScope.launch {
            if (!lobbyCode.value.isNullOrEmpty())
                leaveGameEmitUseCase.invoke(LeaveGameEmitUseCase.LeaveLobbyParam(lobbyCode.value.orEmpty()))
        }
    }

    fun closeLobbyEmit() {
        viewModelScope.launch {
            if (!lobbyCode.value.isNullOrEmpty())
                closeLobbyEmitUseCase.invoke(CloseLobbyEmitUseCase.CloseLobbyParam(lobbyCode.value.orEmpty()))
        }
    }

    fun closeLobbyOn() {
        viewModelScope.launch {
            closeLobbyOnUseCase.invoke().collect { closeLobbyResult ->
                if (closeLobbyResult.isSuccess) {
                    closeLobbyOrGame.postValue(closeLobbyResult.getOrNull())
                } else {
                    Log.i(TAG, closeLobbyResult.exceptionOrNull().toString())
                }
            }
        }
    }

    fun closeGameEmit() {
        viewModelScope.launch {
            if (!lobbyCode.value.isNullOrEmpty())
                closeGameEmitUseCase.invoke(CloseGameEmitUseCase.CloseGameParam(lobbyCode.value.orEmpty()))
        }
    }

    fun closeGameOn() {
        viewModelScope.launch {
            closeGameOnUseCase.invoke().collect { closeGameResult ->
                if (closeGameResult.isSuccess) {
                    closeLobbyOrGame.postValue(closeGameResult.getOrNull())
                } else {
                    Log.i(TAG, closeGameResult.exceptionOrNull().toString())
                }
            }
        }
    }

    fun closeLobbyTimeoutOn() {
        viewModelScope.launch {
            closeLobbyTimeoutOnUseCase.invoke().collect { closeLobbyTimeoutResult ->
                if (closeLobbyTimeoutResult.isSuccess) {
                    closeLobbyOrGame.postValue(closeLobbyTimeoutResult.getOrNull())
                } else {
                    Log.i(TAG, closeLobbyTimeoutResult.exceptionOrNull().toString())
                }
            }
        }
    }

    fun closeGameTimeoutOn() {
        viewModelScope.launch {
            closeGameTimeoutOnUseCase.invoke().collect { closeGameResult ->
                if (closeGameResult.isSuccess) {
                    closeLobbyOrGame.postValue(closeGameResult.getOrNull())
                } else {
                    Log.i(TAG, closeGameResult.exceptionOrNull().toString())
                }
            }
        }
    }

    fun kickUserFromLobbyEmit(user: User) {
        viewModelScope.launch {
            if (!lobbyCode.value.isNullOrEmpty() && user.nickname.isNotEmpty())
                kickUserFromLobbyEmitUseCase.invoke(
                    KickUserFromLobbyEmitUseCase.KickUserFromLobbyParam(
                        lobbyCode.value.orEmpty(),
                        user
                    )
                )
        }
    }

    fun kickUserFromLobbyOn() {
        viewModelScope.launch(Dispatchers.IO) {
            kickUserFromLobbyOnUseCase.invoke().collect { kickUserResult ->
                if (kickUserResult.isSuccess) {
                    kickUser.postValue(kickUserResult.getOrNull())
                } else {
                    Log.i(TAG, kickUserResult.exceptionOrNull().toString())
                }
            }
        }
    }

    fun renewConnectionGameEmit() {
        viewModelScope.launch {
            if (!lobbyCode.value.isNullOrEmpty() && !currentProfile.value?.nickname.isNullOrEmpty())
                renewConnectionGameEmitUseCase.invoke(
                    RenewConnectionGameEmitUseCase.RenewConnectionParam(
                        lobbyCode.value.orEmpty()
                    )
                )
        }
    }

    fun renewConnectionLobbyEmit() {
        viewModelScope.launch {
            if (!lobbyCode.value.isNullOrEmpty() && !currentProfile.value?.nickname.isNullOrEmpty())
                renewConnectionLobbyEmitUseCase.invoke(
                    RenewConnectionLobbyEmitUseCase.RenewConnectionParam(
                        lobbyCode.value.orEmpty()
                    )
                )
        }
    }

    fun getAvatar(avatarName: String): Int {
        return getAvatar.getAvatar(avatarName)
    }

    fun resumeApplication() {
        checkCommunication.postValue(false)
        checkCommunicationEmit()
    }


    fun hardResetLobby() {
        socketDisconnect()
        lobbyCode.postValue("")
        checkCommunication.postValue(false)
        listUser.postValue(mutableListOf())
        resetGameUnits()
        initSocket()
    }

    fun resetLobby() {
        lobbyCode.postValue("")
        listUser.postValue(mutableListOf())
        resetGameUnits()
    }

    private fun resetGameUnits() {
        joinToLobbyBoolean.postValue(false)
        gameStarted.postValue(false)
        wrongLobbyCode.postValue(false)
        checkStartGame.postValue(false)
        closeLobbyOrGame.postValue(false)
        kickUser.postValue(false)
        emptyProfile.postValue(false)
    }

    fun initChoiceLobbyFragment() {
        joinToLobbyBooleanOn()
        gameStartedOrTooMuchPeopleOn()
        wrongLobbyCodeOn()
    }

    fun initCreateLobbyFragment() {
        getLobbyCodeOn()
        getLobbyUsersOn()
        closeLobbyTimeoutOn()
        checkStartGameOn()
        createLobbyEmit()
    }

    fun initJoinLobbyFragment() {
        checkStartGameOn()
        getLobbyUsersOn()
        kickUserFromLobbyOn()
        closeLobbyOn()
        closeLobbyTimeoutOn()
        getLobbyUsersEmit()
    }

    fun initGameAdminFragment() {
        startRoundGameOn()
        closeGameTimeoutOn()
        startRoundEmit()
    }

    fun initGameFragment() {
        startRoundGameOn()
        closeGameTimeoutOn()
        kickUserFromLobbyOn()
        closeGameOn()
    }

}