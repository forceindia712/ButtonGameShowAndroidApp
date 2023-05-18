package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.entities.User
import com.gameshow.button.domain.repositories.ServerCommunicationRepository

class KickUserFromLobbyEmitUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    BaseUseCase<KickUserFromLobbyEmitUseCase.KickUserFromLobbyParam> {

    class KickUserFromLobbyParam(val lobbyCode: String, val user: User):
        BaseUseCase.Params

    override suspend fun invoke(parameter: KickUserFromLobbyParam) {
        serverCommunicationRepository.kickUserFromLobbyEmit(parameter.lobbyCode, parameter.user)
    }
}