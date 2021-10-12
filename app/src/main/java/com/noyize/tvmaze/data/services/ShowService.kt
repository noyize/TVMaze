package com.noyize.tvmaze.data.services

import com.noyize.tvmaze.data.dto.ShowDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ShowService {

    @GET("/shows")
    suspend fun getShows(): List<ShowDto>

    @GET("shows/{id}")
    suspend fun getShow(@Path("id") id: Int): ShowDto

}