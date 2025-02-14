package apis

import Mangadex.Companion.ok
import types.CollectionResponse
import types.EntityResponse
import types.Manga
import types.Tag
import java.util.*

class MangaAPI(private val api: IRetrofitAPI) {
    suspend operator fun invoke(): CollectionResponse<Manga> = api.manga().ok().collection()

    suspend operator fun invoke(id: UUID): EntityResponse<Manga> = api.manga(id).ok().entity()

    suspend fun random(): EntityResponse<Manga> = api.mangaRandom().ok().entity()

    suspend fun tag(): CollectionResponse<Tag> = api.mangaTag().ok().collection()
}