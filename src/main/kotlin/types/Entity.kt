package types

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.util.*

enum class EntityType {
    @JsonProperty("tag")
    TAG,

    @JsonProperty("manga")
    MANGA
}

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = Entity.Tag::class, name = "tag"),
    JsonSubTypes.Type(value = Entity.Manga::class, name = "manga")
)
@JsonIgnoreProperties(ignoreUnknown = true)
sealed class Entity {
    data class Tag(
        val id: UUID = UUID.randomUUID(),
        val type: EntityType = EntityType.TAG,
        val attributes: TagAttributes = TagAttributes()
    ) : Entity()

    data class TagAttributes(
        val name: Map<String, String> = mapOf(),
        val description: Map<String, String> = mapOf(),
        val group: TagGroup = TagGroup.CONTENT,
        val version: Int = 1
    )

    enum class TagGroup {
        @JsonProperty("content")
        CONTENT,

        @JsonProperty("format")
        FORMAT,

        @JsonProperty("genre")
        GENRE,

        @JsonProperty("theme")
        THEME
    }

    data class Manga(
        val id: UUID = UUID.randomUUID(),
        val type: EntityType = EntityType.MANGA
    ) : Entity()
}