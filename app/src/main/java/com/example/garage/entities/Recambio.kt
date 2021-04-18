package com.example.garage.entities

data class Recambio(val referencia: String, val articulo: String, val descipcion: String){

    constructor() :this("","","")
}
