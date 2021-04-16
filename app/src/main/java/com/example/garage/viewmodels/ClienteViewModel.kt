package com.example.garage.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.garage.App
import com.example.garage.entities.Cliente
import com.example.garage.utils.Constantes
import com.google.firebase.firestore.ktx.toObjects


class ClienteViewModel : ViewModel() {

    private val firestore = App.getFirestore()
    private val clientes: MutableLiveData<List<Cliente>> by lazy {
        MutableLiveData<List<Cliente>>().also {
        cargarClientes()
        }
    }


    fun getClientes(): LiveData<List<Cliente>> {
        return clientes
    }

    private fun cargarClientes() {
        firestore.collection(Constantes.CLIENTES).get().addOnSuccessListener {
            val list = it.toObjects<Cliente>()
             clientes.value = list
        }
    }

    fun saveCliente(
        dni: String,
        nombre: String,
        apellido: String,
        apellido2: String,
        direccion: String,
        poblacion: String,
        provincia: String,
        cPostal: Int,
        telefono: Long
    ) {
        val clienteHashMap = hashMapOf(
            "dni" to dni,
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