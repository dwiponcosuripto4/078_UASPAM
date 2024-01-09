package com.example.smilecare

import android.app.Application
import com.example.smilecare.data.AppContainer
import com.example.smilecare.data.BookingContainer

class BookingApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = BookingContainer()
    }
}