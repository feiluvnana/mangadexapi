package types

data class MDAPIException(val response: ErrorResponse<*>) : Throwable()

data class WTFException(val msg: String) : Throwable()