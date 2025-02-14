package types

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode

sealed class MDResponse<T>

data class ErrorResponse<T>(val errors: List<ErrorDetail> = listOf()) : MDResponse<T>() {
    data class ErrorDetail(
        val id: String = "<id>",
        val status: Int = 0,
        val title: String = "<title>",
        val detail: String? = null,
        val context: String? = null
    )
}

sealed class OkResponse<T> : MDResponse<T>() {
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

data class EntityResponse<T>(@Suppress("UNCHECKED_CAST") val data: T = null as T) : OkResponse<T>()

data class CollectionResponse<T>(
    val data: List<T> = listOf(),
    val limit: Int = 0,
    val offset: Int = 0,
    val total: Int = 0
) : OkResponse<T>()

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
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): MDResponse<*> {
        val node = ctxt!!.readTree(p) as ObjectNode
        val result = node.get("result")?.asText()
        val response = node.get("response")?.asText()

        val chapterNode = node.get("chapter")
        return when {
            result == "ok" && response == null && chapterNode != null -> ctxt.readValue(
                node.traverse(),
                ServerUrlResponse::class.java
            )

            result == "ok" && response == "collection" -> {
                val data =
                    ctxt.parser.codec.treeToValue(node.get("data"), List::class.java).map {
                        (ctxt.parser.codec as ObjectMapper).convertValue(it, MDEntity::class.java)
                    }
                CollectionResponse(
                    data = data,
                    limit = ctxt.parser.codec.treeToValue(node.get("limit"), Int::class.java),
                    offset = ctxt.parser.codec.treeToValue(node.get("offset"), Int::class.java),
                    total = ctxt.parser.codec.treeToValue(node.get("total"), Int::class.java)
                )
            }

            result == "ok" && response == "entity" -> EntityResponse(
                data = ctxt.parser.codec.treeToValue(node.get("data"), MDEntity::class.java)
            )

            result == "error" -> p.codec.readValue(node.traverse(), ErrorResponse::class.java)
            else -> throw JsonParseException("MDResponseDeserializer: Unknown result type.")
        }
    }
}


