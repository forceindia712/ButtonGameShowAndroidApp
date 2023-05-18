package com.gameshow.button.domain.usecases.server

import com.gameshow.button.domain.repositories.ServerCommunicationRepository

class InitSocketUseCase(private val serverCommunicationRepository: ServerCommunicationRepository) :
    ParameterlessBaseUseCase {

    override suspend fun invoke() {
        serverCommunicationRepository.initSocket()
    }
}