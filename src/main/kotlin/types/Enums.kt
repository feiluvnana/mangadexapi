package types

import com.fasterxml.jackson.annotation.JsonProperty

enum class EntityType {
    @JsonProperty("tag")
    TAG,

    @JsonProperty("manga")
    MANGA,

    @JsonProperty("author")
    AUTHOR,

    @JsonProperty("manga_relation")
    MANGA_RELATION,

    @JsonProperty("scanlation_group")
    SCANLATION_GROUP,

    @JsonProperty("cover_art")
    COVER_ART,

    @JsonProperty("chapter")
    CHAPTER,

    @JsonProperty("artist")
    ARTIST,

    @JsonProperty("creator")
    CREATOR,
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