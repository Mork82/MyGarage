package com.example.garage.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.garage.App
import com.example.garage.entities.Vehiculo
import com.example.garage.utils.Constantes
import com.google.firebase.firestore.ktx.toObjects

class VehiculoViewModel : ViewModel() {
    private val firestore = App.getFirestore()
    private val auth = App.getAuth()
    private val TAG = "_PRUEBA"

    private val vehiculos: MutableLiveData<List<Vehiculo>> by lazy {
        MutableLiveData<List<Vehiculo>>().also {
            cargarVehiculo()
        }
    }

    fun getVehiculos(): LiveData<List<Vehiculo>> {
        return vehiculos
    }

    private fun cargarVehiculo() {
        firestore.collection(Constantes.USUARIOS).document(auth.currentUser.uid)
            .collection(Constantes.VEHICULO).get().addOnSuccessListener { result ->
                val listData = result.toObjects<Vehiculo>()
                vehiculos.value = listData
            }
    }

    fun saveVehiculo(
        matricula: String,
        bastidor: String,
        marca: String,
        modelo: String,
        color: String,
        neumaticos: String,
        kilometros: Long
    ) {
        val vehiculoHasMap = hashMapOf(
            "matricula" to matricula,
            "bastidor" to bastidor,
            "marca" to marca,
            "modelo" to modelo,
            "color" to color,
            "neumaticos" to neumaticos,
            "kilometros" to kilometros
        )
        firestore.collection(Constantes.USUARIOS).document(auth.currentUser.uid)
            .collection(Constantes.VEHICULO).add(vehiculoHasMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Vehiculo insrtado")
            } else {
                Log.d(TAG, "Vehiculo no insrtado")
            }


        }

    }
}

