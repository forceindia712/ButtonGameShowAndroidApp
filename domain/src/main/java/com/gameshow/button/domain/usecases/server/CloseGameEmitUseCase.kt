package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository

class CloseGameEmitUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    BaseUseCase<CloseGameEmitUseCase.CloseGameParam> {

    class CloseGameParam(val lobbyCode: String): BaseUseCase.Params

    override suspend fun invoke(parameter: CloseGameParam) {
        serverCommunicationRepository.closeGameEmit(parameter.lobbyCode)
    }
}