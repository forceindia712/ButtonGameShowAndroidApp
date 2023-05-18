package com.gameshow.button.domain.usecases.server

interface BaseUseCase<in Parameter: BaseUseCase.Params> {

    interface Params

    suspend operator fun invoke(parameter: Parameter)
}