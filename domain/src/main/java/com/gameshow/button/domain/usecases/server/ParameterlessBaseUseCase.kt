package com.gameshow.button.domain.usecases.server


interface ParameterlessBaseUseCase : BaseUseCase<ParameterlessBaseUseCase.NoParameter> {

    object NoParameter : BaseUseCase.Params

    override suspend fun invoke(parameter: NoParameter) {
        return invoke()
    }

    suspend operator fun invoke()
}