package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.entities.User
import com.gameshow.button.domain.repositories.ServerCommunicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetLobbyUsersOnUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    ParameterlessFlowBaseUseCase<List<User>> {

    override suspend fun invoke(): Flow<Result<List<User>>> {
        return flow {
            try {
                serverCommunicationRepository.getListUsersOn().collect { lobbyList ->
                    emit(lobbyList)
                }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }
}