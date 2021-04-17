package com.example.garage.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.garage.App
import com.example.garage.entities.Cliente
import com.example.garage.utils.Constantes
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.rpc.context.AttributeContext


class ClienteViewModel : ViewModel() {
    private val TAG = "_PRUEBA"
    private val firestore = App.getFirestore()
    private val clientes: MutableLiveData<List<Cliente>> by lazy {
        MutableLiveData<List<Cliente>>().also {
            cargarClientes()
        }
    }
    private val auth = App.getAuth()


    fun getClientes(): LiveData<List<Cliente>> {
        return clientes
    }

    private fun cargarClientes() {
        firestore.collection(Constantes.USUARIOS).document(auth.currentUser.uid).collection(Constantes.CLIENTES).get().addOnSuccessListener {
            val list = mutableListOf<Cliente>()
            for (document in it) {
                val cliente = document.toObject<Cliente>()
                cliente.id = document.id
                list.add(cliente)
            }
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
        firestore.collection(Constantes.USUARIOS).document(auth.currentUser.uid).collection(Constantes.CLIENTES).add(clienteHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //<<<mensaje al usurio
                } else {
                    //mensaje al usuario
                }
            }

    }



}
