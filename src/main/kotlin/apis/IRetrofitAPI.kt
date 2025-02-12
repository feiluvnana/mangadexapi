package apis

import retrofit2.http.GET
import types.Entity
import types.Response

interface IRetrofitAPI {
    @GET("/manga/random")
    suspend fun mangaRandom(): Response<Entity.Manga>

    @GET("/manga/tag")
    suspend fun mangaTag(): Response<Entity.Tag>
}