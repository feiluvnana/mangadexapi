import apis.AtHomeAPI
import apis.IRetrofitAPI
import apis.MangaAPI
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import types.*

suspend fun main() {
    print(Mangadex.manga().data[0].)
}

class Mangadex {
    companion object {
        private val objectMapper: ObjectMapper = ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(
                SimpleModule()
                    .addDeserializer(MDResponse::class.java, MDResponseDeserializer())
                    .addDeserializer(MDEntity::class.java, MDEntityDeserializer())
            )
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mangadex.org")
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
        private val api = retrofit.create(IRetrofitAPI::class.java)

        fun <T> Response<MDResponse<T>>.ok(): OkResponse<T> {
            @Suppress("UNCHECKED_CAST")
            val body: MDResponse<T>? = if (this.isSuccessful) body() else objectMapper.readValue(
                errorBody()?.string(),
                ErrorResponse::class.java
            ) as? MDResponse<T>
            when (body) {
                is ErrorResponse<T> -> throw MDAPIException(body)
                is OkResponse<T> -> return body
                else -> throw WTFException("ok() failed. The type is ${this::class}")
            }
        }

        val atHome: AtHomeAPI = AtHomeAPI(api)
        val manga: MangaAPI = MangaAPI(api)
    }
}

