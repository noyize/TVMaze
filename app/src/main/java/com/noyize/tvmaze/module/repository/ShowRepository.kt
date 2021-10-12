package com.noyize.tvmaze.module.repository

import com.noyize.tvmaze.data.dto.ShowDto

interface ShowRepository {

    suspend fun getShows(): List<ShowDto>

    suspend fun getShowById(id: Int): ShowDto

}