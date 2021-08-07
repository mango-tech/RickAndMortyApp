package com.mango.android.domain.interactor

sealed class Failure {
    object UnknownFailure : Failure()
    object NoInternetFailure : Failure()
    object OutOfBoundsFailure : Failure()
}
