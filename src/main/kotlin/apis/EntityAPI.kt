package apis

import types.*
import java.util.*

class EntityAPI(api: IRetrofitAPI) {

    val manga = MangaEndpoint(api)
    val tag = TagEndpoint(api)

    class MangaEndpoint(private val api: IRetrofitAPI) {
        suspend operator fun invoke(): CollectionResponse<Manga> = api.manga().ok().collection()

        suspend operator fun invoke(id: UUID): EntityResponse<Manga> = api.manga(id).ok().entity()

        suspend fun random(): EntityResponse<Manga> = api.mangaRandom().ok().entity()
    }

    class TagEndpoint(private val api: IRetrofitAPI) {
        suspend operator fun invoke(): CollectionResponse<Tag> = api.mangaTag().ok().collection()
    }
}