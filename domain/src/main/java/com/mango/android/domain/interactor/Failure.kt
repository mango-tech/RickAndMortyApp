package com.mango.android.domain.interactor

sealed class Failure {
    object UnknownFailure : Failure()
    object NetworkFailure : Failure()
    object OutOfBoundsFailure : Failure()
}
