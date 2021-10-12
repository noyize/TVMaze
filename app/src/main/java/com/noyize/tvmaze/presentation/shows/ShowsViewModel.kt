package com.noyize.tvmaze.presentation.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noyize.tvmaze.module.model.Show
import com.noyize.tvmaze.module.repository.ShowRepository
import com.noyize.tvmaze.util.Resource
import com.noyize.tvmaze.util.toShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
  private val  showRepository: ShowRepository
) : ViewModel() {

    private val _shows = MutableStateFlow<Resource<List<Show>>>(Resource.Loading)
    val shows : StateFlow<Resource<List<Show>>> = _shows

    init {
        getCoins()
    }

    private fun getCoins() = viewModelScope.launch {
        try {
            val coins = showRepository.getShows().map { it.toShow() }
            _shows.emit(Resource.Success(coins))
        } catch (e: HttpException) {
            _shows.emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            _shows.emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}

