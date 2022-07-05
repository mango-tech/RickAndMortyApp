package es.andres.bailen.domain.models

data class DataResult<out T>(
    val status: Status,
    val data: T?,
    val errorType: ErrorType = ErrorType.GENERIC,
    val message: String? = null,
) {

    enum class Status {
        SUCCESS,
        ERROR
    }


    enum class ErrorType {
        GENERIC,
        NETWORK
    }

    companion object {
        fun <T> success(data: T): DataResult<T> {
            return DataResult(Status.SUCCESS, data)
        }

        fun <T> error(
            data: T? = null,
            errorType: ErrorType = ErrorType.GENERIC,
            message: String? = null
        ): DataResult<T> {
            return DataResult(Status.ERROR, data, errorType, message)
        }

    }
}