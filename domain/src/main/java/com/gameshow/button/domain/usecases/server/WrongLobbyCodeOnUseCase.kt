package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WrongLobbyCodeOnUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    ParameterlessFlowBaseUseCase<Boolean> {

    override suspend fun invoke(): Flow<Result<Boolean>> {
        return flow {
            try {
                serverCommunicationRepository.wrongLobbyCodeOn().collect { wrongLobbyCode ->
                    emit(wrongLobbyCode)
                }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }
}