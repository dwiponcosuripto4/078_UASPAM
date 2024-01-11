package com.example.smilecare.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smilecare.data.BookingRepository
import com.example.smilecare.model.Booking
import com.example.smilecare.ui.HomeUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed class BookingUIState {
    data class Success(val booking: Flow<List<Booking>>) : BookingUIState()
    object Error : BookingUIState()
    object Loading : BookingUIState()
}

class HomeViewModel(private val bookingRepository: BookingRepository) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    val homeUIState: StateFlow<HomeUIState> = bookingRepository.getAllBookingStream()
        .filterNotNull()
        .map {bookingList ->
            val filteredList = bookingList.filter { booking ->
                booking.jenisPerawatan.contains(_searchQuery.value, ignoreCase = true) ||
                        booking.tanggal.contains(_searchQuery.value, ignoreCase = true) ||
                        booking.status.contains(_searchQuery.value, ignoreCase = true)
            }
            HomeUIState (listBooking = filteredList ) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUIState()

        )
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
}

data class HomeUiState(
    val listBooking: List<Booking> = listOf()
)