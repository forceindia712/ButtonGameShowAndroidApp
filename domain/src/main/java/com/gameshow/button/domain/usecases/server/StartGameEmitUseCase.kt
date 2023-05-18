package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository

class StartGameEmitUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    BaseUseCase<StartGameEmitUseCase.StartGameParam> {

    class StartGameParam(val lobbyCode: String): BaseUseCase.Params

    override suspend fun invoke(parameter: StartGameParam) {
        serverCommunicationRepository.startGameEmit(parameter.lobbyCode)
    }
}