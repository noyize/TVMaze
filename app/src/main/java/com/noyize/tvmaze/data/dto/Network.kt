package com.noyize.tvmaze.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Network(
    @Json(name = "country")
    val country: Country?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?
) : Parcelable