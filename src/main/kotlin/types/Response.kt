package types

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

enum class ResultType {
    @JsonProperty("ok")
    OK,

    @JsonProperty("error")
    ERROR
}

enum class ResponseType {
    @JsonProperty("entity")
    ENTITY,

    @JsonProperty("collection")
    COLLECTION,

    @JsonProperty("error")
    ERROR
}

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "response"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = Response.Entity::class, name = "entity"),
    JsonSubTypes.Type(value = Response.Collection::class, name = "collection"),
    JsonSubTypes.Type(value = Response.Error::class, name = "error")
)
@JsonIgnoreProperties(ignoreUnknown = true)
sealed class Response<T> {
    data class Entity<T>(
        val result: ResultType = ResultType.OK,
        val response: ResponseType = ResponseType.ENTITY,
        @Suppress("UNCHECKED_CAST") val data: T = null as T
    ) : Response<T>()


    data class Collection<T>(
        val result: ResultType = ResultType.OK,
        val response: ResponseType = ResponseType.COLLECTION,
        val data: List<T> = listOf(),
        val limit: Int = 0,
        val offset: Int = 0,
        val total: Int = 0
    ) : Response<T>()

    data class Error<T>(
        val result: ResultType = ResultType.ERROR,
        val response: ResponseType = ResponseType.ERROR,
        val errors: List<ErrorDetail> = listOf()
    ) : Response<T>()

    fun collection(): Collection<T> {
        when (this) {
            is Collection -> return this
            is Error -> throw MDAPIException(this)
            is Entity -> throw WTFException()
        }
    }

    fun entity(): Entity<T> {
        when (this) {
            is Entity -> return this
            is Error -> throw MDAPIException(this)
            is Collection -> throw WTFException()
        }
    }
}

data class ErrorDetail(val id: String, val status: Int, val title: String, val detail: String?, val context: String?)