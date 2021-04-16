package com.example.garage.viewmodels

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.garage.App
import com.example.garage.entities.Usuario
import com.example.garage.utils.Constantes
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.UserProfileChangeRequest
import kotlin.Exception

class UsuarioViewModel : ViewModel() {

   private val auth = App.getAuth()

    fun login(email: String, Password: String): LiveData<Task<AuthResult>> {
        val data = MutableLiveData<Task<AuthResult>>()
        auth.signInWithEmailAndPassword(email, Password)
            .addOnCompleteListener(Activity()) { task ->
                data.value = task
            }
        return data
    }

    fun registro(usuario: Usuario, password: String): MutableLiveData<Exception?> {
        val data = MutableLiveData<Exception?>()

        auth.createUserWithEmailAndPassword(usuario.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //Peticion para cambiar el perfil
                    auth.currentUser.apply {
                        //Peticion para cojer el dato del Edit text
                        val profile = UserProfileChangeRequest.Builder().setDisplayName(
                            usuario.nombre
                        ).build()
                        //Actualizar consulta

                        updateProfile(profile).addOnCompleteListener { taskChangeProfile ->
                            if (taskChangeProfile.isSuccessful) {
                                App.getFirestore()
                                    .collection(Constantes.USUARIOS)
                                    .document(auth.currentUser.uid)
                                    .set(
                                        usuario
                                    ).addOnCompleteListener { taskNewUser ->
                                        if (taskNewUser.isSuccessful) {
                                            if (taskNewUser.isSuccessful) {
                                                data.value = null
                                            } else {
                                                data.value = taskNewUser.exception
                                            }
                                        }

                                    }
                            } else {
                                data.value = taskChangeProfile.exception
                            }

                        }

                    }
                } else {
                    data.value = task.exception
                }
            }
        return data
    }
}



