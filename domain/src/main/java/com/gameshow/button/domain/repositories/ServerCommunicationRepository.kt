package com.gameshow.button.domain.repositories

import kotlinx.coroutines.flow.Flow

interface ServerCommunicationRepository {

    suspend fun initSocket()
    suspend fun disconnect()
    suspend fun checkConnectionEmit()
    suspend fun checkConnectionOn(): Flow<Result<Boolean>>
    suspend fun createLobbyEmit(profile: com.gameshow.button.domain.entities.Profile)
    suspend fun getListUsersOn(): Flow<Result<List<com.gameshow.button.domain.entities.User>>>
    suspend fun joinToLobbyBooleanEmit(user: com.gameshow.button.domain.entities.Profile, lobbyCode: String)
    suspend fun joinToLobbyBooleanOn(): Flow<Result<Boolean>>
    suspend fun getLobbyCodeOn(): Flow<Result<String>>
    suspend fun wrongLobbyCodeOn(): Flow<Result<Boolean>>
    suspend fun gameStartedOrTooMuchPeopleOn(): Flow<Result<Boolean>>
    suspend fun getLobbyUsersEmit(lobbyCode: String)
    suspend fun startGameEmit(lobbyCode: String)
    suspend fun checkStartGameOn(): Flow<Result<Boolean>>
    suspend fun startRoundEmit(lobbyCode: String)
    suspend fun buttonClickedEmit(lobbyCode: String)
    suspend fun leaveLobbyEmit(lobbyCode: String)
    suspend fun leaveGameEmit(lobbyCode: String)
    suspend fun closeLobbyEmit(lobbyCode: String)
    suspend fun closeLobbyOn(): Flow<Result<Boolean>>
    suspend fun closeGameEmit(lobbyCode: String)
    suspend fun closeGameOn(): Flow<Result<Boolean>>
    suspend fun closeLobbyTimeoutOn(): Flow<Result<Boolean>>
    suspend fun closeGameTimeoutOn(): Flow<Result<Boolean>>
    suspend fun kickUserFromLobbyEmit(lobbyCode: String, user: com.gameshow.button.domain.entities.User)
    suspend fun kickUserFromLobbyOn(): Flow<Result<Boolean>>
    suspend fun renewConnectionLobbyEmit(lobbyCode: String)
    suspend fun renewConnectionGameEmit(lobbyCode: String)
    suspend fun getLogOn(): Flow<Result<List<String>>>
}