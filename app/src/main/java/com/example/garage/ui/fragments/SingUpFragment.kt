package com.example.garage.ui.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.garage.App
import com.example.garage.R
import com.example.garage.entityes.Usuario
import com.example.garage.databinding.FragmentSingUpBinding
import com.example.garage.utils.Constantes
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


class SingUpFragment : Fragment() {
    private val TAG = "MAIN_ACTIVITY"
    private var _binding: FragmentSingUpBinding? = null
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingUpBinding.inflate(inflater, container, false)
        val binding = _binding!!
        val view = binding.root

        // Initialize Firebase Auth
        auth = Firebase.auth



        binding.singUpBtnCancelar.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_singUpFragment_to_loginFragment)
        }
        binding.singUpBtnAceptar.setOnClickListener {
            val email = binding.singUpTieEmail
            val password1 = binding.singUpTiePass1

            if (email.getString().isNullOrBlank()) {
                Snackbar.make(view, R.string.field_null, Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (password1.getString().isNullOrBlank()) {
                Snackbar.make(view, R.string.field_null, Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(email.getString(), password1.getString())
                .addOnCompleteListener(Activity()) { task ->
                    if (task.isSuccessful) {

                        //Peticion para cambiar el perfil
                        auth.currentUser.apply {

                            //Peticion para cojer el dato del Edit text
                            val profile = UserProfileChangeRequest.Builder().setDisplayName(
                                binding.singUpTieName.getString()
                            ).build()
                            //Actualizar consulta

                            updateProfile(profile).addOnCompleteListener { taskChangeProfile ->
                                if (taskChangeProfile.isSuccessful) {
                                    App.getFirestore()
                                        .collection(Constantes.USUARIOS)
                                        .document(auth.currentUser.uid)
                                        .set(
                                            Usuario(
                                                binding.singUpTieName.getString(),
                                                binding.singUpTieEmail.getString(),
                                                binding.singUpTieEmpresa.getString(),
                                                binding.singUpTiePhone.getString(),
                                                Date(System.currentTimeMillis()),
                                                Date(System.currentTimeMillis())
                                            )
                                        ).addOnCompleteListener{taskNewUser->
                                            if (taskNewUser.isSuccessful){
                                              // NavHostFragment.findNavController(this).navigate(R.id.action_singUpFragment_to_loginFragment)
                                            }else{
                                                Snackbar.make(view, getString(R.string.no_complete_load), Snackbar.LENGTH_LONG)
                                                    .show()
                                            }


                                        }

                                } else {
                                    Snackbar.make(
                                        view,
                                        getString(R.string.error_change_username),
                                        Snackbar.LENGTH_LONG
                                    )
                                        .show()
                                }


                            }

                        }

                        NavHostFragment.findNavController(this)
                            .navigate(R.id.action_singUpFragment_to_loginFragment)
                    } else {
                        // If sign in fails, display a message to the user.
                        when (task.exception) {
                            is FirebaseAuthWeakPasswordException -> {
                                Snackbar.make(view, R.string.password_weak, Snackbar.LENGTH_LONG)
                                    .show()
                            }
                            else -> {
                                Snackbar.make(view, R.string.fail_register, Snackbar.LENGTH_LONG)

                            }
                        }

                    }
                }

        }





        return view
    }

    fun TextInputEditText.getString(): String {
        return text.toString()
    }
}