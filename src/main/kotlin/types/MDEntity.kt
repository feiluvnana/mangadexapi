package types

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.util.*

sealed class MDEntity(val id: UUID = UUID.randomUUID(), val relationships: List<Relationship> = listOf())

data class Tag(val attributes: TagAttributes = TagAttributes()) :
    MDEntity()

data class TagAttributes(
    val name: Map<String, String> = mapOf(),
    val description: Map<String, String> = mapOf(),
    val group: TagGroup = TagGroup.CONTENT,
    val version: Int = 0
)

data class Manga(val attributes: MangaAttributes = MangaAttributes()) : MDEntity()

data class MangaAttributes(
    val title: Map<String, String> = mapOf(),
    val altTitles: List<Map<String, String>> = listOf(),
)

data class Relationship(val type: EntityType = EntityType.TAG) : MDEntity()

class MDEntityDeserializer : JsonDeserializer<MDEntity>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): MDEntity {
        val node = ctxt!!.readTree(p)
        val type = node.get("type").asText()
        val attributesNode = node.get("attributes")
        return when {
            type == "tag" && attributesNode != null -> ctxt.parser.codec.treeToValue(node, Tag::class.java)

            type == "manga" && attributesNode != null -> ctxt.parser.codec.treeToValue(node, Manga::class.java)

            else -> throw JsonParseException("MDResponseDeserializer: Unknown result type.")
        }
    }
}