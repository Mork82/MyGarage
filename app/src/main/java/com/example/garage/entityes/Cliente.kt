package com.example.garage.entityes

data class Cliente(
    val nombre: String,
    val apellido: String,
    val apellido2: String,
    val direccion: String,
    val poblacion: String,
    val provincia: String,
    val cPostal: Int,
    val telefono: Int
) {
    constructor(): this("","","","","","",0,0)
}
