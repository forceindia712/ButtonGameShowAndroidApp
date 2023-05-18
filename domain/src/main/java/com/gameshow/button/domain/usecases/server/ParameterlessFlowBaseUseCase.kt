package com.gameshow.button.domain.usecases.server

import kotlinx.coroutines.flow.Flow


interface ParameterlessFlowBaseUseCase<out DataType : Any>
    : FlowBaseUseCase<ParameterlessFlowBaseUseCase.NoParameter, DataType> {

    object NoParameter : FlowBaseUseCase.Params

    override suspend fun invoke(parameter: NoParameter): Flow<Result<DataType>> {
        return invoke()
    }

    suspend operator fun invoke(): Flow<Result<DataType>>
}