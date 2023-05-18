package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.entities.Profile
import com.gameshow.button.domain.repositories.ServerCommunicationRepository

class JoinToLobbyBooleanEmitUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    BaseUseCase<JoinToLobbyBooleanEmitUseCase.JoinToLobbyBooleanParam> {

    class JoinToLobbyBooleanParam(val profile: Profile, val lobbyCode: String):
        BaseUseCase.Params

    override suspend fun invoke(parameter: JoinToLobbyBooleanParam) {
        serverCommunicationRepository.joinToLobbyBooleanEmit(parameter.profile, parameter.lobbyCode)
    }
}