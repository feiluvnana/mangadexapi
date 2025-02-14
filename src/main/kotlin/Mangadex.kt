import apis.AtHomeAPI
import apis.EntityAPI
import apis.IRetrofitAPI
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import types.MDEntity
import types.MDEntityDeserializer
import types.MDResponse
import types.MDResponseDeserializer

suspend fun main() {
    print(Mangadex.entity.manga().data[0].attributes)
}

class Mangadex {
    companion object {
        val objectMapper: ObjectMapper = ObjectMapper()
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

        val atHome: AtHomeAPI = AtHomeAPI(api)
        val entity: EntityAPI = EntityAPI(api)
    }
}

