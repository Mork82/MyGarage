package com.example.garage

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class App:Application (){
    companion object{
        fun getAuth(): FirebaseAuth {
            return Firebase.auth
        }
        fun getFirestore(): FirebaseFirestore {
            return Firebase.firestore
        }
    }
}