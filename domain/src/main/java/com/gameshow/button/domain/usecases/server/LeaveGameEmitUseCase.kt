package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository

class LeaveGameEmitUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    BaseUseCase<LeaveGameEmitUseCase.LeaveLobbyParam> {

    class LeaveLobbyParam(val lobbyCode: String): BaseUseCase.Params

    override suspend fun invoke(parameter: LeaveLobbyParam) {
        serverCommunicationRepository.leaveLobbyEmit(parameter.lobbyCode)
    }
}