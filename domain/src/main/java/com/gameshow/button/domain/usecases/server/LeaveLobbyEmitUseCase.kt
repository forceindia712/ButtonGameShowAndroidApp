package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository

class LeaveLobbyEmitUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    BaseUseCase<LeaveLobbyEmitUseCase.LeaveLobbyParam> {

    class LeaveLobbyParam(val lobbyCode: String): BaseUseCase.Params

    override suspend fun invoke(parameter: LeaveLobbyParam) {
        serverCommunicationRepository.leaveLobbyEmit(parameter.lobbyCode)
    }
}