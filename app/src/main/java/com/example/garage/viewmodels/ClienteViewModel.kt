package com.example.garage.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.garage.App
import com.example.garage.entityes.Cliente
import kotlin.collections.hashMapOf as hashMapOf1

class ClienteViewModel : ViewModel() {

    private val firestore = App.getFirestore()
    private val clientes: MutableLiveData<List<Cliente>> by lazy {
        MutableLiveData<List<Cliente>>().also {

        }
    }

    fun saveCliente(
        nombre: String,
        apellido: String,
        apellido2: String,
        direccion: String,
        poblacion: String,
        provincia: String,
        cPostal: Int,
        telefono: Int
    ) {
        val clienteHashMap = kotlin.collections.hashMapOf(
            "nombre" to nombre,
            "apellido" to apellido,
            "apellido2" to apellido2,
            "direccion" to direccion,
            "poblacion" to poblacion,
            "provincia" to provincia,
            "cPostal" to cPostal,
            "telefono" to telefono
        )
        firestore.collection("clientes").add(clienteHashMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //<<<mensaje al usurio
            } else {
                //mensaje al usuario
            }
        }
    }
}