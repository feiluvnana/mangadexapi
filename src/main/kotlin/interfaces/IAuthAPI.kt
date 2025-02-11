package interfaces

import AuthenticateResponse
import GrantType
import RefreshResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IRawAuthAPI {
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

class IAuthAPI(private val iRawAuthAPI: IRawAuthAPI) {
    suspend fun authenticate(
        username: String,
        password: String,
        clientId: String,
        clientSecret: String
    ): AuthenticateResponse {
        return iRawAuthAPI.token(
            grantType = GrantType.PASSWORD,
            username = username,
            password = password,
            clientId = clientId,
            clientSecret = clientSecret
        ) as AuthenticateResponse
    }

    suspend fun refresh(
        refreshToken: String,
        clientId: String,
        clientSecret: String
    ): RefreshResponse {
        return iRawAuthAPI.token(
            grantType = GrantType.REFRESH_TOKEN,
            refreshToken = refreshToken,
            clientId = clientId,
            clientSecret = clientSecret
        ) as RefreshResponse
    }
}