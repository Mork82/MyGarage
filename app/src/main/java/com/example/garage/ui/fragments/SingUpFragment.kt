package com.example.garage.ui.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.garage.R
import com.example.garage.entityes.Usuario
import com.example.garage.databinding.FragmentSingUpBinding
import com.example.garage.viewmodels.UsuarioViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestoreException
import java.util.*


class SingUpFragment : Fragment() {
    private val TAG = "MAIN_ACTIVITY"
    private var _binding: FragmentSingUpBinding? = null
    private val model: UsuarioViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

            if (email.getString().isNullOrBlank()) {
                Snackbar.make(view, R.string.field_null, Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (password1.getString().isNullOrBlank()) {
                Snackbar.make(view, R.string.field_null, Snackbar.LENGTH_LONG).show()
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
                            is FirebaseAuthWeakPasswordException -> {
                                Snackbar.make(
                                    view,
                                    getString(R.string.password_weak),
                                    Snackbar.LENGTH_LONG
                                ).show()

                            }
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