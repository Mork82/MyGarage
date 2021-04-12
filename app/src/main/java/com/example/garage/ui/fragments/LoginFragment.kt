package com.example.garage.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.NavHostFragment
import com.example.garage.R
import com.example.garage.databinding.FragmentLoginBinding
import com.example.garage.ui.activitys.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    private val TAG = "MAIN_ACTIVITY"
    private var _binding: FragmentLoginBinding? = null
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val view = _binding!!.root
        val binding = _binding!!

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.loginTiePassword.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    binding.loginTilPassword.error = ""
                }
            }
        }

        //texto añadido donde co mprabamos que el edit text tiene suçize caracteres
        binding.loginTiePassword.addTextChangedListener {
            val size = it!!.length
            if (size < 6) {
                binding.loginTilPassword.error = "La contraseña tiene que tener $size/6 caracteres"
            } else {
                binding.loginTilPassword.error = ""
            }
        }
        binding.loginTiePassword.setOnClickListener {
            val textInputEditText = it as TextInputEditText //Casteo
            //Recupermaos el texto del edit text lo convertimnos en string y comprobamos su longuitud
            val size = textInputEditText.text.toString().length
            if (size < 6) {
                binding.loginTilPassword.error = "La contraseña tiene que tener $size/6"
            } else {
                binding.loginTilPassword.error = ""
            }
        }

        binding.loginBtnLogin.setOnClickListener {
            val editPassword = binding.loginTiePassword
            val password = editPassword.text

            val editUser = binding.loginTieUser
            val user = editUser.text

            if (password.isNullOrBlank()) {
                binding.loginTilPassword.error = getString(R.string.error_blank)
                return@setOnClickListener
            }

            if (user.isNullOrBlank()) {
                binding.loginTilUser.error = getString(R.string.user_blank)
                return@setOnClickListener
            }
            val email = binding.loginTieUser
            auth.signInWithEmailAndPassword(email.getString(), editPassword.getString())
                .addOnCompleteListener(Activity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        goToCars()

                    } else {
                        // If sign in fails, display a message to the user.
                        Snackbar.make(view, getString(R.string.error_sesion), Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
        }

        binding.loginBtnSingUp.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_loginFragment_to_singUpFragment)
        }

        binding.loginBtnRestore.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_loginFragment_to_recoverPassFragment)
        }


        return view

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser


        if (currentUser != null) {
            Log.d(TAG, currentUser!!.email!!)
            //Log.d(TAG, currentUser!!.displayName!!)
            Log.d(TAG, currentUser!!.uid)
            goToCars()
        }
    }

    private fun goToCars() {

        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)

    }

    fun TextInputEditText.getString(): String {
        return text.toString()
    }
}




