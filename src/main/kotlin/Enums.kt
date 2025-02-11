import com.google.gson.annotations.SerializedName

enum class GrantType {
    @SerializedName("password")
    PASSWORD,

    @SerializedName("refresh_token")
    REFRESH_TOKEN
}