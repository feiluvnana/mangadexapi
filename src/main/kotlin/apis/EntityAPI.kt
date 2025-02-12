package apis

import types.Entity
import types.Response

class EntityAPI(api: IRetrofitAPI) {
    val manga = Manga(api)
    val tag = Tag(api)

    class Manga(private val api: IRetrofitAPI) {
        suspend fun random(): Response.Entity<Entity.Manga> = api.mangaRandom().entity()
    }

    class Tag(private val api: IRetrofitAPI) {
        suspend operator fun invoke(): Response.Collection<Entity.Tag> = api.mangaTag().collection()
    }
}