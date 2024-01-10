package com.example.smilecare.ui.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smilecare.data.BookingRepository
import com.example.smilecare.ui.DetailBooking
import com.example.smilecare.ui.UIStateBooking
import com.example.smilecare.ui.toBooking
import com.example.smilecare.ui.toUiStateBooking
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: BookingRepository
) : ViewModel() {

    var bookingUiState by mutableStateOf(UIStateBooking())
        private set

    private val bookingId: String = checkNotNull(savedStateHandle[EditDestination.bookingId])

    init {
        viewModelScope.launch {
            bookingUiState =
                repository.getBookingById(bookingId)
                    .filterNotNull()
                    .first()
                    .toUiStateBooking()
        }
    }

    fun updateUIState(detailBooking: DetailBooking) {
        bookingUiState = bookingUiState.copy(detailBooking = detailBooking)
    }

    suspend fun updateBooking() {
        repository.update(bookingUiState.detailBooking.toBooking())

    }
}