package types

import com.fasterxml.jackson.annotation.JsonProperty

enum class EntityType {
    @JsonProperty("tag")
    TAG,

    @JsonProperty("manga")
    MANGA
}

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