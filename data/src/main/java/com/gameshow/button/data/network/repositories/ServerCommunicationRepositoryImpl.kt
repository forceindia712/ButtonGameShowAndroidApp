package com.gameshow.button.data.network.repositories

import com.gameshow.button.data.network.mapper.ProfileMapper
import com.gameshow.button.data.network.mapper.UserMapper
import com.gameshow.button.data.network.objects.SocketHandler
import com.gameshow.button.domain.entities.Profile
import com.gameshow.button.domain.entities.User
import com.gameshow.button.domain.repositories.ServerCommunicationRepository
import io.socket.client.Socket
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch

class ServerCommunicationRepositoryImpl(
    private val profileMapper: ProfileMapper,
    private val userMapper: UserMapper,
    private val server_address: String
) : ServerCommunicationRepository {

    lateinit var mSocket: Socket

    override suspend fun initSocket() {
        SocketHandler.setSocket(server_address)
        mSocket = SocketHandler.getSocket()
        mSocket.connect()
    }

    override suspend fun disconnect() {
        mSocket.disconnect()
    }

    override suspend fun checkConnectionEmit() {
        mSocket.emit("checkConnection")
    }

    override suspend fun checkConnectionOn(): Flow<Result<Boolean>> = callbackFlow {
        mSocket.on("checkConnection") {
            trySendBlocking(Result.success(true))
        }
        awaitClose { mSocket.disconnect() }
    }.catch { error ->
        emit(Result.failure(error))
    }

    override suspend fun createLobbyEmit(profile: Profile) {
        mSocket.emit("createLobby", profileMapper.transform(profile))
    }

    override suspend fun getListUsersOn(): Flow<Result<List<User>>> = callbackFlow {
        mSocket.on("leaveGame") { args ->
            val lobbyListUsersJSON = args[0] as String
            val lobbyListUsers = userMapper.transformToRepository(lobbyListUsersJSON)
            trySendBlocking(Result.success(lobbyListUsers))
        }
        mSocket.on("sendLobbyUsers") { args ->
            val lobbyListUsersJSON = args[0] as String
            val lobbyListUsers = userMapper.transformToRepository(lobbyListUsersJSON)
            trySendBlocking(Result.success(lobbyListUsers))
        }
        mSocket.on("startRound") { args ->
            val lobbyListUsersJSON = args[0] as String
            val lobbyListUsers = userMapper.transformToRepository(lobbyListUsersJSON)
            trySendBlocking(Result.success(lobbyListUsers))
        }
        mSocket.on("buttonClicked") { args ->
            val lobbyListUsersJSON = args[0] as String
            val lobbyListUsers = userMapper.transformToRepository(lobbyListUsersJSON)
            trySendBlocking(Result.success(lobbyListUsers))
        }
        mSocket.on("leaveLobby") { args ->
            val lobbyListUsersJSON = args[0] as String
            val lobbyListUsers = userMapper.transformToRepository(lobbyListUsersJSON)
            trySendBlocking(Result.success(lobbyListUsers))
        }
        mSocket.on("leaveGame") { args ->
            val lobbyListUsersJSON = args[0] as String
            val lobbyListUsers = userMapper.transformToRepository(lobbyListUsersJSON)
            trySendBlocking(Result.success(lobbyListUsers))
        }
        awaitClose { mSocket.disconnect() }
    }.catch { error ->
        emit(Result.failure(error))
    }


    override suspend fun joinToLobbyBooleanEmit(user: Profile, lobbyCode: String) {
        mSocket.emit("joinLobby", profileMapper.transform(user), lobbyCode)
    }

    override suspend fun joinToLobbyBooleanOn(): Flow<Result<Boolean>> = callbackFlow {
        mSocket.on("enterIntoLobby") {
            trySendBlocking(Result.success(true))
        }
        awaitClose { mSocket.disconnect() }
    }.catch { error ->
        emit(Result.failure(error))
    }

    override suspend fun getLobbyCodeOn(): Flow<Result<String>> = callbackFlow {
        mSocket.on("sendLobbyCode") { args ->
            val lobbyCode = args[0] as String
            trySendBlocking(Result.success(lobbyCode))
        }
        awaitClose { mSocket.disconnect() }
    }.catch { error ->
        emit(Result.failure(error))
    }

    override suspend fun wrongLobbyCodeOn(): Flow<Result<Boolean>> = callbackFlow {
        mSocket.on("wrongLobbyCode") {
            trySendBlocking(Result.success(true))
        }
        awaitClose { mSocket.disconnect() }
    }.catch { error ->
        emit(Result.failure(error))
    }

    override suspend fun gameStartedOrTooMuchPeopleOn(): Flow<Result<Boolean>> = callbackFlow {
        mSocket.on("gameStartedOrTooMuchPeople") {
            trySendBlocking(Result.success(true))
        }
        awaitClose { mSocket.disconnect() }
    }.catch { error ->
        emit(Result.failure(error))
    }

    override suspend fun getLobbyUsersEmit(lobbyCode: String) {
        mSocket.emit("getLobbyUsers", lobbyCode)
    }

    override suspend fun startGameEmit(lobbyCode: String) {
        mSocket.emit("startGame", lobbyCode)
    }

    override suspend fun checkStartGameOn(): Flow<Result<Boolean>> = callbackFlow {
        mSocket.on("startGame") {
            trySendBlocking(Result.success(true))
        }
        awaitClose { mSocket.disconnect() }
    }.catch { error ->
        emit(Result.failure(error))
    }

    override suspend fun startRoundEmit(lobbyCode: String) {
        mSocket.emit("startRound", lobbyCode)
    }

    override suspend fun buttonClickedEmit(lobbyCode: String) {
        mSocket.emit("buttonClicked", lobbyCode)
    }

    override suspend fun leaveLobbyEmit(lobbyCode: String) {
        mSocket.emit("leaveLobby", lobbyCode)
    }

    override suspend fun leaveGameEmit(lobbyCode: String) {
        mSocket.emit("leaveGame", lobbyCode)
    }

    override suspend fun closeLobbyEmit(lobbyCode: String) {
        mSocket.emit("closeLobby", lobbyCode)
    }

    override suspend fun closeLobbyOn(): Flow<Result<Boolean>> = callbackFlow {
        mSocket.on("closeLobby") {
            trySendBlocking(Result.success(true))
        }
        awaitClose { mSocket.disconnect() }
    }.catch { error ->
        emit(Result.failure(error))
    }

    override suspend fun closeGameEmit(lobbyCode: String) {
        mSocket.emit("closeGame", lobbyCode)
    }

    override suspend fun closeGameOn(): Flow<Result<Boolean>> = callbackFlow {
        mSocket.on("closeGame") {
            trySendBlocking(Result.success(true))
        }
        awaitClose { mSocket.disconnect() }
    }.catch { error ->
        emit(Result.failure(error))
    }

    override suspend fun closeLobbyTimeoutOn(): Flow<Result<Boolean>> = callbackFlow {
        mSocket.on("closeNotActiveLobby") {
            trySendBlocking(Result.success(true))
        }
        awaitClose { mSocket.disconnect() }
    }.catch { error ->
        emit(Result.failure(error))
    }

    override suspend fun closeGameTimeoutOn(): Flow<Result<Boolean>> = callbackFlow {
        mSocket.on("closeNotActiveGame") {
            trySendBlocking(Result.success(true))
        }
        awaitClose { mSocket.disconnect() }
    }.catch { error ->
        emit(Result.failure(error))
    }

    override suspend fun kickUserFromLobbyEmit(lobbyCode: String, user: User) {
        mSocket.emit("kickUserFromLobby", lobbyCode, user.socketID)
    }

    override suspend fun kickUserFromLobbyOn(): Flow<Result<Boolean>> = callbackFlow {
        mSocket.on("kickUserFromLobby") {
            trySendBlocking(Result.success(true))
        }
        awaitClose { mSocket.disconnect() }
    }.catch { error ->
        emit(Result.failure(error))
    }

    override suspend fun renewConnectionLobbyEmit(lobbyCode: String) {
        mSocket.emit("renewConnectionLobby", lobbyCode)
    }

    override suspend fun renewConnectionGameEmit(lobbyCode: String) {
        mSocket.emit("renewConnectionGame", lobbyCode)
    }

    override suspend fun getLogOn(): Flow<Result<List<String>>> {
        TODO("Not yet implemented")
    }

}