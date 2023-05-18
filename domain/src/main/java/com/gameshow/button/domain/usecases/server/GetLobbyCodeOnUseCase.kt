package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetLobbyCodeOnUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    ParameterlessFlowBaseUseCase<String> {

    override suspend fun invoke(): Flow<Result<String>> {
        return flow {
            try {
                serverCommunicationRepository.getLobbyCodeOn().collect { lobbyCode ->
                    emit(lobbyCode)
                }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }
}