package com.example.smilecare.model

data class Booking(
    val id: String,
    val tanggal: String,
    val waktu: String,
    val jenisPerawatan: String,
    val status: String
) {
    constructor() : this("", "", "", "", "")
}