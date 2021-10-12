package com.noyize.tvmaze.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Externals(
    @Json(name = "imdb")
    val imdb: String?,
    @Json(name = "thetvdb")
    val thetvdb: Int?,
    @Json(name = "tvrage")
    val tvrage: Int?
) : Parcelable