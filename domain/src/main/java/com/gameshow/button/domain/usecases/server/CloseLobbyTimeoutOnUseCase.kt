package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CloseLobbyTimeoutOnUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    ParameterlessFlowBaseUseCase<Boolean> {

    override suspend fun invoke(): Flow<Result<Boolean>> {
        return flow {
            try {
                serverCommunicationRepository.closeLobbyTimeoutOn().collect { closeLobbyTimeout ->
                    emit(closeLobbyTimeout)
                }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }
}