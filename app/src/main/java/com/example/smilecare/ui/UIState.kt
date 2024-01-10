package com.example.smilecare.ui

import com.example.smilecare.model.Booking

data class UIStateBooking(
    val detailBooking: DetailBooking= DetailBooking(),
)

data class DetailBooking(
    val id: String = "",
    val waktu: String = "",
    val jenisPerawatan: String = "",
    val catatanKhusus: String = "",
    val status: String = "",
    val nomorAntrian: String = "",
    val tanggal: String = ""
)

/* Fungsi untuk mengkonversi data input ke data dalam tabel sesuai jenis datanya*/
fun DetailBooking.toBooking(): Booking = Booking(
    id = id,
    waktu = waktu,
    jenisPerawatan = jenisPerawatan,
    catatanKhusus = catatanKhusus,
    status = status,
    nomorAntrian = nomorAntrian,
    tanggal = tanggal
)

data class DetailUIState(
    val addEvent: DetailBooking = DetailBooking(),
)

fun Booking.toDetailBooking(): DetailBooking =
    DetailBooking(
        id = id,
        waktu = waktu,
        jenisPerawatan = jenisPerawatan,
        catatanKhusus = catatanKhusus,
        status = status,
        nomorAntrian = nomorAntrian,
        tanggal = tanggal
    )

fun Booking.toUiStateBooking(): UIStateBooking = UIStateBooking(
    detailBooking = this.toDetailBooking()
)

data class HomeUIState(
    val listBooking: List<Booking> = listOf(),
    val dataLength: Int = 0
)