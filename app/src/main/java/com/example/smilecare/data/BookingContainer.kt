package com.example.smilecare.data

import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer{
    val bookingRepository:BookingRepository
}
class BookingContainer : AppContainer {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override val bookingRepository: BookingRepository by lazy {
        BookingRepositoryImpl(firestore)
    }
}