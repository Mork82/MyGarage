package com.example.garage.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.garage.App
import com.example.garage.entities.Recambio
import com.example.garage.utils.Constantes
import com.google.firebase.firestore.ktx.toObjects

class RecambioViewModel : ViewModel() {
    private val firestore = App.getFirestore()
    private val auth = App.getAuth()
    private val TAG = "_PRUEBA"

    private val recambios: MutableLiveData<List<Recambio>> by lazy {

        MutableLiveData<List<Recambio>>().also {
            cargarRecambios()
        }
    }

    fun getRecambios(): LiveData<List<Recambio>> {
        return recambios
    }

    private fun cargarRecambios() {
        firestore.collection(Constantes.USUARIOS).document(auth.currentUser.uid)
            .collection(Constantes.RECAMBIO).get().addOnSuccessListener { result ->
                val listData = result.toObjects<Recambio>()
                recambios.value = listData
            }
    }

    fun saveRecambio(referencia: String, articulo: String, descripcion: String) {
        val recambioHashMap = hashMapOf(
            "referencia" to referencia,
            "articulo" to articulo,
            "descripcion" to descripcion
        )
        firestore.collection(Constantes.USUARIOS).document(auth.currentUser.uid)
            .collection(Constantes.RECAMBIO).add(recambioHashMap).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Insertado")
                } else {
                    Log.d(TAG, "Insertado")
                }
            }
    }
}
