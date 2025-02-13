package apis

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import types.MDResponse
import types.Manga
import types.Tag
import java.util.*

interface IRetrofitAPI {
    @GET("/at-home/server/{chapterId}")
    suspend fun atHomeServer(@Path("chapterId") chapterId: UUID): Response<MDResponse<Any>>

    @GET("/manga")
    suspend fun manga(): Response<MDResponse<Manga>>

    @GET("/manga/{id}")
    suspend fun manga(@Path("id") id: UUID): Response<MDResponse<Manga>>

    @GET("/manga/random")
    suspend fun mangaRandom(): Response<MDResponse<Manga>>

    @GET("/manga/tag")
    suspend fun mangaTag(): Response<MDResponse<Tag>>
}