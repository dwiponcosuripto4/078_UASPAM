package com.example.smilecare.model

data class Booking(
    val id: String,
    val nama: String,
    val alamat: String,
    val telpon: String,
    val waktu: String,
    val jenisPerawatan: String,
    val catatanKhusus: String,
    val status: String,
    val nomorAntrian: String,
    val tanggal: String,
) {
    constructor() : this("","","","","","", "", "Pending", "", "")
}
