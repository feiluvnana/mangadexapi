package apis

import Mangadex.Companion.ok
import java.util.*

class AtHomeAPI(private val api: IRetrofitAPI) {
    suspend fun chapter(chapterId: UUID) = api.atHomeServer(chapterId).ok().serverUrl()
}