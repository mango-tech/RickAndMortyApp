package com.mango.android.domain.interactor

interface UseCase<out Type, in Params> {

    fun run(params: Params, onResult: (OneOf<Failure, Type>) -> Unit)
}