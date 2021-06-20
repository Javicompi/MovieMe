package es.jnsoft.movieme.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "status_message")
    val statusMessage: String,
    @Json(name = "status_code")
    val statusCode: Int
)