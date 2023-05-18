package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository

class RenewConnectionLobbyEmitUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    BaseUseCase<RenewConnectionLobbyEmitUseCase.RenewConnectionParam> {

    class RenewConnectionParam(val lobbyCode: String): BaseUseCase.Params

    override suspend fun invoke(parameter: RenewConnectionParam) {
        serverCommunicationRepository.renewConnectionLobbyEmit(parameter.lobbyCode)
    }
}