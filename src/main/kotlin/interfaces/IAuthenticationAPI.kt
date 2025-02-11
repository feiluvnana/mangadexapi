package interfaces

import retrofit2.http.GET
import retrofit2.http.Header

interface IAuthenticationAPI {
    @GET("/auth/check")
    suspend fun readPermission(@Header("Authorization") token: String): Any
}