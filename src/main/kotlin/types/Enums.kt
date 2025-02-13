package types

import com.fasterxml.jackson.annotation.JsonProperty

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

    @JsonProperty("server_url")
    SERVER_URL
}

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