package com.noyize.tvmaze.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Schedule(
    @Json(name = "days")
    val days: List<String>?,
    @Json(name = "time")
    val time: String?
) : Parcelable