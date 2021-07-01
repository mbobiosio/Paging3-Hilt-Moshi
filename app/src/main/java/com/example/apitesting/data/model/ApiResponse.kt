package com.example.apitesting.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "data")
    val `data`: Data,
    @Json(name = "message")
    val message: String,
    @Json(name = "status")
    val status: String
)