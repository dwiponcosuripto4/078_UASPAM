package com.example.smilecare.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.smilecare.BookingApplication

fun CreationExtras.aplikasiBooking(): BookingApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookingApplication)

object PenyediaViewModel {
    val Factory = viewModelFactory {


    }
}