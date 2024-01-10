package com.example.smilecare.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.smilecare.BookingApplication
import com.example.smilecare.ui.add.AddViewModel
import com.example.smilecare.ui.detail.DetailViewModel
import com.example.smilecare.ui.edit.EditViewModel
import com.example.smilecare.ui.home.HomeViewModel

fun CreationExtras.aplikasiBooking(): BookingApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookingApplication)

object PenyediaViewModel {
    val Factory = viewModelFactory {

        initializer {
            AddViewModel(aplikasiBooking().container.bookingRepository)
        }

        initializer {
            HomeViewModel(aplikasiBooking().container.bookingRepository)
        }

        initializer {
            DetailViewModel(
                createSavedStateHandle(),
                aplikasiBooking().container.bookingRepository
            )
        }

        initializer {
            EditViewModel(
                createSavedStateHandle(),
                aplikasiBooking().container.bookingRepository
            )
        }
    }
}