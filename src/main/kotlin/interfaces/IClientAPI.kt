package interfaces

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

interface IClientAPI {
    @GET("/client")
    suspend fun read(): Any

    @POST("/client")
    suspend fun create(): Any

    @GET("/client/{id}")
    suspend fun read(@Path("id") id: UUID): Any

    @POST("/client/{id}")
    suspend fun update(@Path("id") id: UUID): Any

    @DELETE("/client/{id}")
    suspend fun delete(@Path("id") id: UUID): Any

    @GET("/client/{id}/secret")
    suspend fun readSecret(@Path("id") id: UUID): Any

    @POST("/client/{id}/secret")
    suspend fun createSecret(@Path("id") id: UUID): Any
}
