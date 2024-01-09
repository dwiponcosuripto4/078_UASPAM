package com.example.smilecare.model

data class Booking(
    val id: String,
    val jenisPerawatan: String,
    val tanggal: String,
    val waktu: String,
    val status: String
) {
    constructor() : this("", "", "", "", "")
}