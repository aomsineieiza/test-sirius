package com.test.sirius.base

import com.test.sirius.network.Result

abstract class CoroutinesUseCase<in P, R> {
    abstract suspend fun execute(parameter: P): Result<R>
}