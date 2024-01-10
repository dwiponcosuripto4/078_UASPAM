package com.example.smilecare.data

import android.content.ContentValues
import android.util.Log
import com.example.smilecare.model.Booking
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

interface BookingRepository{
    fun getAll(): Flow<List<Booking>>
    suspend fun save(booking: Booking):String
    suspend fun update(booking: Booking)
    suspend fun delete(bookingId: String)
    fun getBookingById(bookingId: String): Flow<Booking>
}
class BookingRepositoryImpl(private val firestore: FirebaseFirestore):BookingRepository{
    override fun getAll(): Flow<List<Booking>> = flow{
        val snapshot = firestore.collection("Booking")
            .orderBy("jenisPerawatan", Query.Direction.ASCENDING)
            .get()
            .await()
        val booking = snapshot.toObjects(Booking::class.java)
        emit(booking)
    }.flowOn(Dispatchers.IO)

    override suspend fun save(booking: Booking): String {
        return try {
            val documentReference = firestore.collection("Booking").add(booking).await()

            firestore.collection("Booking").document(documentReference.id)
                .set(booking.copy(id = documentReference.id))
            "Berhasil + ${documentReference.id}"
        } catch (e: Exception){
            Log.w(ContentValues.TAG,"Error adding document",e)
            "Gagal $e"
        }
    }

    override suspend fun update(booking: Booking) {
        firestore.collection("Booking").document(booking.id).set(booking).await()
    }

    override suspend fun delete(bookingId: String) {
        firestore.collection("Booking").document(bookingId).delete().await()
    }

    override fun getBookingById(bookingId: String): Flow<Booking> {
        return flow {
            val snapshot = firestore.collection("Booking").document(bookingId).get().await()
            val booking = snapshot.toObject(Booking::class.java)
            emit(booking!!)
        }.flowOn(Dispatchers.IO)
    }

}