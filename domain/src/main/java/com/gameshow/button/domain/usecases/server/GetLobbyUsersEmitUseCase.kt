package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository

class GetLobbyUsersEmitUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    BaseUseCase<GetLobbyUsersEmitUseCase.LobbyUsersParam> {

    class LobbyUsersParam(val lobbyCode: String): BaseUseCase.Params

    override suspend fun invoke(parameter: LobbyUsersParam) {
        serverCommunicationRepository.getLobbyUsersEmit(parameter.lobbyCode)
    }
}