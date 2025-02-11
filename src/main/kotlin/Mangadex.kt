import interfaces.*
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

suspend fun main() {
    try {
        println(
            Mangadex.manga.read(UUID.fromString("f9c33607-9180-4ba6-b85c-e4b5faee7192"))
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

        val auth: IAuthAPI = IAuthAPI(
            retrofit.newBuilder()
                .baseUrl("https://auth.mangadex.org")
                .build()
                .create(IRawAuthAPI::class.java)
        )
        val client: IClientAPI = retrofit.create(IClientAPI::class.java)
        val atHome: IAtHomeAPI = retrofit.create(IAtHomeAPI::class.java)
        val author: IAuthorAPI = retrofit.create(IAuthorAPI::class.java)
        val manga: IMangaAPI = retrofit.create(IMangaAPI::class.java)
    }
}

