package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository

class ButtonClickedEmitUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    BaseUseCase<ButtonClickedEmitUseCase.ButtonClickedParam> {

    class ButtonClickedParam(val lobbyCode: String): BaseUseCase.Params

    override suspend fun invoke(parameter: ButtonClickedParam) {
        serverCommunicationRepository.buttonClickedEmit(parameter.lobbyCode)
    }
}