package com.gameshow.button.domain.usecases.server

import kotlinx.coroutines.flow.Flow

interface FlowBaseUseCase<in Parameter: FlowBaseUseCase.Params, out DataType: Any> {

    interface Params

    suspend operator fun invoke(parameter: Parameter): Flow<Result<DataType>>
}