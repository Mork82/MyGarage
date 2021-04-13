package com.example.garage.viewmodels

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.garage.App
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class UsuarioViewModel : ViewModel() {

    val auth = App.getAuth()

    fun login(email: String, Password: String): LiveData<Task<AuthResult>> {
        val data = MutableLiveData<Task<AuthResult>>()
        auth.signInWithEmailAndPassword(email, Password)
            .addOnCompleteListener(Activity()) { task ->
                data.value = task
            }
        return data
    }

    fun registro(email: String, Password: String) {

    }
}



