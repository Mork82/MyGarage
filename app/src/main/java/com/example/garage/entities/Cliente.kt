package com.example.garage.entities

data class Cliente(
    val dni: String,
    val nombre: String,
    val apellido: String,
    val apellido2: String,
    val direccion: String,
    val poblacion: String,
    val provincia: String,
    val cPostal: Int,
    val telefono: Long
) {

    lateinit var id: String

    constructor() : this("", "", "", "", "", "", "", 0, 0)
}
