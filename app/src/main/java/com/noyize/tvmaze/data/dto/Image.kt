package com.noyize.tvmaze.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Image(
    @Json(name = "medium")
    val medium: String,
    @Json(name = "original")
    val original: String
) : Parcelable