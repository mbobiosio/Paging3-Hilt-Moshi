package com.example.apitesting.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "firstname")
    val firstname: String?,
    @Json(name = "id")
    val id: String,
    @Json(name = "lastname")
    val lastname: String?,
    @Json(name = "username")
    val username: String
)