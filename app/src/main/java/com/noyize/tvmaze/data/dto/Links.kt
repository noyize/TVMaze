package com.noyize.tvmaze.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Links(
    @Json(name = "previousepisode")
    val previousepisode: Previousepisode?,
    @Json(name = "self")
    val self: Self?
) : Parcelable