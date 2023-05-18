package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository

class StartRoundEmitUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    BaseUseCase<StartRoundEmitUseCase.StartRoundParam> {

    class StartRoundParam(val lobbyCode: String): BaseUseCase.Params

    override suspend fun invoke(parameter: StartRoundParam) {
        serverCommunicationRepository.startRoundEmit(parameter.lobbyCode)
    }
}