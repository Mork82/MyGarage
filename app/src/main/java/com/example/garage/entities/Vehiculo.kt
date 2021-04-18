package com.example.garage.entities

data class Vehiculo(
    val matricula: String,
    val bastidor: String,
    val marca: String,
    val modelo: String,
    val color:String,
    val neumaticos:String,
    val kilometros:Long
){
    lateinit var id:String

    constructor():this("","","","","","",0)
}
