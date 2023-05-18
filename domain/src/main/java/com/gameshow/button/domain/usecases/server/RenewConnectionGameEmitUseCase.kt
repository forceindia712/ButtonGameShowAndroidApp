package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository

class RenewConnectionGameEmitUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    BaseUseCase<RenewConnectionGameEmitUseCase.RenewConnectionParam> {

    class RenewConnectionParam(val lobbyCode: String): BaseUseCase.Params

    override suspend fun invoke(parameter: RenewConnectionParam) {
        serverCommunicationRepository.renewConnectionGameEmit(parameter.lobbyCode)
    }
}