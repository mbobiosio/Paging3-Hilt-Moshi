package com.example.apitesting.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "total")
    val total: Int,
    @Json(name = "users")
    val users: List<User>
)