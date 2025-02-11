import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*

suspend fun main() {
    try {
        println(
            Mangadex.auth.authenticate(
                username = "fei",
                password = "rune",
                clientId = "id",
                clientSecret = "secret"
            )
        )
    } catch (e: HttpException) {
        println(e.response())
    }
}

class Mangadex {
    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mangadex.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val auth: AuthAPI = AuthAPI(
            retrofit.newBuilder()
                .baseUrl("https://auth.mangadex.org")
                .build()
                .create(RawAuthAPI::class.java)
        )
        val manga: MangaAPI = retrofit.create(MangaAPI::class.java)
    }
}

interface RawAuthAPI {
    @FormUrlEncoded
    @POST("/realms/mangadex/protocol/openid-connect/token")
    suspend fun token(
        @Field("grant_type") grantType: GrantType,
        @Field("username") username: String? = null,
        @Field("password") password: String? = null,
        @Field("refresh_token") refreshToken: String? = null,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Any
}

class AuthAPI(private val rawAuthAPI: RawAuthAPI) {
    suspend fun authenticate(
        username: String,
        password: String,
        clientId: String,
        clientSecret: String
    ): AuthenticateResponse {
        return rawAuthAPI.token(
            grantType = GrantType.PASSWORD,
            username = username,
            password = password,
            clientId = clientId,
            clientSecret = clientSecret
        ) as AuthenticateResponse
    }

    suspend fun refresh(
        username: String,
        password: String,
        clientId: String,
        clientSecret: String
    ): RefreshResponse {
        return rawAuthAPI.token(
            grantType = GrantType.REFRESH_TOKEN,
            username = username,
            password = password,
            clientId = clientId,
            clientSecret = clientSecret
        ) as RefreshResponse
    }
}

interface MangaAPI {
    @GET("/manga/{id}")
    suspend fun id(@Path("id") id: UUID): Any

    @GET("/manga/random")
    suspend fun random(): Any

    @GET("/manga/tag")
    suspend fun tag(): Any

    @GET("/manga")
    suspend operator fun invoke(): Any
}