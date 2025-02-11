package interfaces

import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface IAtHomeAPI {
    @GET("/at-home/server/{chapterId}")
    suspend fun readServerUrl(@Path("chapterId") chapterId: UUID): Any
}