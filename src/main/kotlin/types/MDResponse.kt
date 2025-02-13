package types

import Mangadex
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.node.ObjectNode
import retrofit2.Response


sealed class MDResponse<T>(val result: ResultType = ResultType.OK)

fun <T> Response<MDResponse<T>>.ok(): OkResponse<T> {
    @Suppress("UNCHECKED_CAST")
    val body: MDResponse<T>? = if (this.isSuccessful) body() else Mangadex.objectMapper.readValue(
        errorBody()?.string(),
        ErrorResponse::class.java
    ) as? MDResponse<T>
    when (body) {
        is ErrorResponse<T> -> throw MDAPIException(body)
        is OkResponse<T> -> return body
        else -> throw WTFException("ok() failed. The type is ${this::class}")
    }
}

data class ErrorResponse<T>(val errors: List<ErrorDetail> = listOf()) : MDResponse<T>(result = ResultType.ERROR) {
    data class ErrorDetail(
        val id: String = "<id>",
        val status: Int = 0,
        val title: String = "<title>",
        val detail: String? = null,
        val context: String? = null
    )
}

sealed class OkResponse<T>(val response: ResponseType = ResponseType.SERVER_URL) :
    MDResponse<T>(result = ResultType.OK) {
    fun collection(): CollectionResponse<T> {
        when (this) {
            is CollectionResponse<T> -> return this
            else -> throw WTFException("collection() failed. The type is ${this::class}")
        }
    }

    fun entity(): EntityResponse<T> {
        when (this) {
            is EntityResponse<T> -> return this
            else -> throw WTFException("entity() failed. The type is ${this::class}")
        }
    }

    fun serverUrl(): ServerUrlResponse {
        when (this) {
            is ServerUrlResponse -> return this
            else -> throw WTFException("serverUrl() failed. The type is ${this::class}")
        }
    }
}

data class EntityResponse<T>(@Suppress("UNCHECKED_CAST") val data: T = null as T) :
    OkResponse<T>(response = ResponseType.ENTITY)

data class CollectionResponse<T>(
    val data: List<T> = listOf(),
    val limit: Int = 0,
    val offset: Int = 0,
    val total: Int = 0
) : OkResponse<T>(response = ResponseType.COLLECTION)

data class ServerUrlResponse(
    val baseUrl: String = "<baseUrl>",
    val chapter: ChapterUrl = ChapterUrl()
) : OkResponse<Any>() {
    data class ChapterUrl(
        val hash: String = "<hash>",
        val data: List<String> = listOf(),
        val dataSaver: List<String> = listOf()
    )
}

class MDResponseDeserializer : JsonDeserializer<MDResponse<*>>() {
    override fun deserialize(p0: JsonParser, p1: DeserializationContext?): MDResponse<*> {
        val node = p0.codec.readTree<ObjectNode>(p0)
        return when (node.get("result").asText()) {
            "ok" -> {
                val responseNode = node.get("response")
                when {
                    responseNode == null -> p0.codec.treeToValue(node, ServerUrlResponse::class.java)
                    responseNode.asText() == "collection" -> p0.codec.treeToValue(node, CollectionResponse::class.java)
                    responseNode.asText() == "entity" -> p0.codec.treeToValue(node, EntityResponse::class.java)
                    else -> throw JsonParseException("MDResponseDeserializer: Unknown response type.")
                }
            }

            "error" -> p0.codec.treeToValue(node, ErrorResponse::class.java)
            else -> throw JsonParseException("MDResponseDeserializer: Unknown result type.")
        }
    }
}


