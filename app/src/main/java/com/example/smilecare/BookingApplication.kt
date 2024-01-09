package com.example.smilecare

import com.example.smilecare.data.BookingRepository
import com.example.smilecare.data.BookingRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer{
    val bookingRepository: BookingRepository
}
class BookingContainer : AppContainer {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override val bookingRepository: BookingRepository by lazy {
        BookingRepositoryImpl(firestore)
    }
}