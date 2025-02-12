import apis.EntityAPI
import apis.IRetrofitAPI
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

suspend fun main() {
    println(Mangadex.entity.tag())
    Int.hashCode()
}

class Mangadex {
    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mangadex.org")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        val entity: EntityAPI = EntityAPI(retrofit.create(IRetrofitAPI::class.java))
    }
}

