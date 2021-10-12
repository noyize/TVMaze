package com.noyize.tvmaze.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noyize.tvmaze.module.model.ShowDetail
import com.noyize.tvmaze.module.repository.ShowRepository
import com.noyize.tvmaze.util.Constants
import com.noyize.tvmaze.util.Resource
import com.noyize.tvmaze.util.toShowDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val showRepository: ShowRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _showDetailState = MutableStateFlow<Resource<ShowDetail>>(Resource.Loading)
    val showDetailState: StateFlow<Resource<ShowDetail>> = _showDetailState

    init {
        getCoinById()
    }

    private fun getCoinById() = viewModelScope.launch {
        savedStateHandle.get<Int>(Constants.PARAM_SHOW_ID)?.let {
            try {
                val show = showRepository.getShowById(it).toShowDetail()
                _showDetailState.emit(Resource.Success(show))
            } catch (e: HttpException) {
                _showDetailState.emit(
                    Resource.Error(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                _showDetailState.emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
        }
    }
}