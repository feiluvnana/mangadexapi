package interfaces

import retrofit2.http.*
import java.util.*

interface IAuthorAPI {
    @GET("/author")
    suspend fun read(): Any

    @POST("/author")
    suspend fun create(): Any

    @GET("/author/{id}")
    suspend fun read(@Path("id") id: UUID): Any

    @PUT("/author/{id}")
    suspend fun update(@Path("id") id: UUID): Any

    @DELETE("/author/{id}")
    suspend fun delete(@Path("id") id: UUID): Any
}