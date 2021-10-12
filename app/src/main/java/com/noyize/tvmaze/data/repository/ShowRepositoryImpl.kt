package com.noyize.tvmaze.data.repository

import com.noyize.tvmaze.data.dto.ShowDto
import com.noyize.tvmaze.data.services.ShowService
import com.noyize.tvmaze.module.repository.ShowRepository
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(private val showService: ShowService) : ShowRepository {

    override suspend fun getShows(): List<ShowDto> {
        return showService.getShows()
    }

    override suspend fun getShowById(id: Int): ShowDto {
       return showService.getShow(id)
    }
}