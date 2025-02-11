package interfaces

import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface IMangaAPI {
    @GET("/manga/{id}")
    suspend fun read(@Path("id") id: UUID): Any

    @GET("/manga/random")
    suspend fun readRandom(): Any

    @GET("/manga/tag")
    suspend fun readTag(): Any

    @GET("/manga")
    suspend operator fun invoke(): Any
}