package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class JoinToLobbyBooleanOnUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    ParameterlessFlowBaseUseCase<Boolean> {

    override suspend fun invoke(): Flow<Result<Boolean>> {
        return flow {
            try {
                serverCommunicationRepository.joinToLobbyBooleanOn().collect { joinToLobbyBoolean ->
                    emit(joinToLobbyBoolean)
                }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }
}