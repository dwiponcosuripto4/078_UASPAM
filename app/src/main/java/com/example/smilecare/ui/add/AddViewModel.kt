package com.example.smilecare.ui.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.smilecare.data.BookingRepository
import com.example.smilecare.ui.DetailBooking
import com.example.smilecare.ui.UIStateBooking
import com.example.smilecare.ui.toBooking

class AddViewModel(private val bookingRepository: BookingRepository): ViewModel(){
    /**
     * Berisi status siswa saat ini
     */
    var uiStateBooking by mutableStateOf(UIStateBooking())
        private set

    /* Fungsi untuk memvalidasi input */

    fun updateUiState(detailBooking: DetailBooking) {
        uiStateBooking=
            UIStateBooking(detailBooking = detailBooking,)
    }

    /*Fungsi untuk menyimpan data yang di-entry */
    suspend fun saveBooking(){
        bookingRepository.save(uiStateBooking.detailBooking.toBooking())
    }
}