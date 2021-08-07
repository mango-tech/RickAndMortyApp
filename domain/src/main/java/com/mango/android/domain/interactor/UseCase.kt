package com.mango.android.domain.interactor

interface UseCase<out Type, in Params> {

    suspend fun exec(params: Params, onResult: (OneOf<Failure, Type>) -> Unit)
}