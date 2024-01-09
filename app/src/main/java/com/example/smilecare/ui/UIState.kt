package com.example.smilecare.ui

import com.example.smilecare.model.Booking

data class UIStateBooking(
    val detailBooking: DetailBooking= DetailBooking(),
)

data class  DetailBooking(
    val id: String = "",
    val jenisPerawatan : String= "",
    val tanggal : String= "",
    val waktu : String= "",
    val status : String= ""
)

/* Fungsi untuk mengkonversi data input ke data dalam tabel sesuai jenis datanya*/
fun DetailBooking.toBooking(): Booking = Booking(
    id = id,
    jenisPerawatan = jenisPerawatan,
    tanggal = tanggal,
    waktu = waktu,
    status = status
)

data class DetailUIState(
    val addEvent: DetailBooking = DetailBooking(),
)

fun Booking.toDetailBooking(): DetailBooking =
    DetailBooking(
        id = id,
        jenisPerawatan = jenisPerawatan,
        tanggal = tanggal,
        waktu = waktu,
        status = status
    )

fun Booking.toUiStateBooking(): UIStateBooking = UIStateBooking(
    detailBooking = this.toDetailBooking()
)

data class HomeUIState(
    val listBooking: List<Booking> = listOf(),
    val dataLength: Int = 0
)