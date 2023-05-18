package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository

class CloseLobbyEmitUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    BaseUseCase<CloseLobbyEmitUseCase.CloseLobbyParam> {

    class CloseLobbyParam(val lobbyCode: String): BaseUseCase.Params

    override suspend fun invoke(parameter: CloseLobbyParam) {
        serverCommunicationRepository.closeLobbyEmit(parameter.lobbyCode)
    }
}