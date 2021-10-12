package com.noyize.tvmaze.util

import com.noyize.tvmaze.data.dto.ShowDto
import com.noyize.tvmaze.module.model.Show
import com.noyize.tvmaze.module.model.ShowDetail

fun ShowDto.toShow(): Show{
    return Show(
        id=id,
        name = name,
        summary = summary,
        imageUrl = image.medium,
        language = language,
        rating = rating?.average.toString(),
        status = status
    )
}

fun ShowDto.toShowDetail(): ShowDetail {
    return ShowDetail(
        id = id,
        name = name,
        summary = summary,
        imageUrl = image.medium,
        language = language,
        rating = rating?.average.toString(),
        status = status,
        genres = genres,
        website =  officialSite.toString()
    )
}