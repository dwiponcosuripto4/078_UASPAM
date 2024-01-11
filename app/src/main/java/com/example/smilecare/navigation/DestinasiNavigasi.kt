package com.example.smilecare.navigation

interface DestinasiNavigasi {
    val route: String

    val titleRes : String
}
object DestinasiLanding : DestinasiNavigasi {
    override val route = "landing"
    override val titleRes = "Landing"
}