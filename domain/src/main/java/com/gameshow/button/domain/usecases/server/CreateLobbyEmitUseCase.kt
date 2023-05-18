package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.entities.Profile
import com.gameshow.button.domain.repositories.ServerCommunicationRepository

class CreateLobbyEmitUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    BaseUseCase<CreateLobbyEmitUseCase.CreateLobbyParam> {

    class CreateLobbyParam(val profile: Profile):
        BaseUseCase.Params

    override suspend fun invoke(parameter: CreateLobbyParam) {
        serverCommunicationRepository.createLobbyEmit(parameter.profile)
    }
}