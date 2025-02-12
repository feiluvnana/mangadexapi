package types

class MDAPIException(val response: Response.Error<*>) : Throwable()

class WTFException : Throwable()