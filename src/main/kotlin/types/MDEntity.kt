package types

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.util.*

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = Tag::class, name = "tag"),
    JsonSubTypes.Type(value = Manga::class, name = "manga")
)
@JsonIgnoreProperties(ignoreUnknown = true)
sealed class MDEntity(val id: UUID = UUID.randomUUID(), val type: EntityType = EntityType.TAG)

data class Tag(val attributes: TagAttributes = TagAttributes()) : MDEntity()

data class TagAttributes(
    val name: Map<String, String> = mapOf(),
    val description: Map<String, String> = mapOf(),
    val group: TagGroup = TagGroup.CONTENT,
    val version: Int = 0
)

data class Manga(val attributes: MangaAttributes) : MDEntity()

data class MangaAttributes(
    val title: Map<String, String>
)