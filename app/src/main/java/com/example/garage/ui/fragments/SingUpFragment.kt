package com.example.garage.ui.fragments


import android.os.Bundle
import android.util.Patterns
import android.util.Patterns.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.garage.R
import com.example.garage.entities.Usuario
import com.example.garage.databinding.FragmentSingUpBinding
import com.example.garage.viewmodels.UsuarioViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

import com.google.firebase.firestore.FirebaseFirestoreException
import java.util.*
import java.util.regex.Pattern


class SingUpFragment : Fragment() {
    private val TAG = "MAIN_ACTIVITY"
    private var _binding: FragmentSingUpBinding? = null
    private val model: UsuarioViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingUpBinding.inflate(inflater, container, false)
        val binding = _binding!!
        val view = binding.root





        binding.singUpBtnCancelar.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_singUpFragment_to_loginFragment)

        }
        binding.singUpBtnAceptar.setOnClickListener {
            val email = binding.singUpTieEmail
            val password1 = binding.singUpTiePass1
            val password2 = binding.singUpTiePass2
            //Comprobacion para saber si el email esta en blanco
            if (EMAIL_ADDRESS.matcher(email.getString()).matches() && email.getString().isBlank()) {
                Snackbar.make(view, getString(R.string.email_null), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
           /* if (validarEmail(email.getString())!=true){
                Snackbar.make(view,"El formato del email debe ser correcto",Snackbar.LENGTH_LONG)

                }*/
            //Comnprobacion para saber si el pass esta en blanco
            if (password1.getString().isBlank()) {
                Snackbar.make(view, getString(R.string.password_null), Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //Comnprobacion para saber si el pas 2 esta en blanco
            if (password2.getString().isBlank()) {
                Snackbar.make(view, R.string.password_null, Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //Comprobacion par saber si el password tiene mas de 6 caracteres
            if (password1.getString().length < 6) {
                binding.singUpTilContrasenya.error = getString(R.string.password_weak)
                return@setOnClickListener

            }
            //Comprbacion para saber si los dos passwords son iguales
            if (!password1.getString().equals(password2.getString())) {
                binding.singUpTilContrasenya.error = getString(R.string.contrasenyas_iguales)
                binding.singUpTilRepetirContrasenya.error =
                    getString(R.string.contrasenyas_iguales)
                return@setOnClickListener
            }
            val usuario = Usuario(
                binding.singUpTieName.getString(),
                binding.singUpTieEmail.getString(),
                binding.singUpTieEmpresa.getString(),
                binding.singUpTiePhone.getString(),
                Date(System.currentTimeMillis()),
                Date(System.currentTimeMillis())
            )
            binding.progresLayout.myProgressBar.visibility = View.VISIBLE
            model.registro(usuario, password1.getString())
                .observe(viewLifecycleOwner, { exception ->
                    if (exception == null) {

                        binding.progresLayout.myProgressBar.visibility = View.GONE

                    } else {
                        when (exception) {
                            is FirebaseAuthUserCollisionException -> {
                                Snackbar.make(
                                    view,
                                    getString(R.string.email_in_use),
                                    Snackbar.LENGTH_LONG
                                )
                                    .show()
                            }

                            is FirebaseFirestoreException -> {
                                Snackbar.make(
                                    view,
                                    getString(R.string.error_add_user_collection),
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                            is FirebaseAuthInvalidUserException -> {
                                Snackbar.make(
                                    view,
                                    getString(R.string.error_change_username),
                                    Snackbar.LENGTH_LONG
                                ).show()

                            }
                           /* is FirebaseAuthWeakPasswordException -> {
                                Snackbar.make(
                                    view,
                                    getString(R.string.password_weak),
                                    Snackbar.LENGTH_LONG
                                ).show()

                            }*/
                            else -> {
                                Snackbar.make(
                                    view,
                                    getString(R.string.fail_register),
                                    Snackbar.LENGTH_LONG
                                ).show()

                            }
                        }
                    }


                })


        }





        return view
    }



    fun TextInputEditText.getString(): String {
        return text.toString()
    }
}