package com.example.smilecare.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smilecare.data.BookingRepository
import com.example.smilecare.ui.DetailUIState
import com.example.smilecare.ui.toDetailBooking
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: BookingRepository
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val bookingId: String = checkNotNull(savedStateHandle[DetailDestination.bookingId])

    val uiState: StateFlow<DetailUIState> =
        repository.getBookingById(bookingId)
            .filterNotNull()
            .map {
                DetailUIState(addEvent = it.toDetailBooking())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DetailUIState()
            )

    suspend fun deleteBooking() {
        repository.delete(bookingId)

    }


}