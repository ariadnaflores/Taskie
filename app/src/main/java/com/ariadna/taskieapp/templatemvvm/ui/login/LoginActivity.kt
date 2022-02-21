package com.ariadna.taskieapp.templatemvvm.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ariadna.taskieapp.R
import com.ariadna.taskieapp.databinding.ActivityLoginBinding
import com.ariadna.taskieapp.templatemvvm.ui.home.HomeActivity
import com.ariadna.taskieapp.templatemvvm.ui.login.viewModel.*
import com.ariadna.taskieapp.templatemvvm.ui.onboarding.OnBoardingActivity
import com.ariadna.taskieapp.templatemvvm.ui.utils.FormValidations
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by lazy {
        val factory = LoginViewModelFactory(FormValidations())
        ViewModelProvider(this@LoginActivity, factory)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.buttonBack.setOnClickListener {
            // val intentBackOnBoarding = Intent(this, OnBoardingActivity::class.java)
            // startActivity(intentBackOnBoarding)
            // FIXME
            finish()
        }
        subscribeObservers()
        binding.buttonAccountLogin.setOnClickListener {
            //validate()
            viewModel.checkFields(
                userEmail = binding.edtEmail.editText?.text.toString(),
                userPassword = binding.edtPassword.editText?.text.toString()
            )
        }
    }

    private fun subscribeObservers() {
        viewModel.loginViewStateLiveData.observe(this) {
            when (it) {
                UserEmptyEmail -> {
                    //FIXME: put strings as resources
                    binding.edtEmail.error = getString(R.string.form_error_empty_email)
                }
                UserInvalidEmail -> {
                    binding.edtEmail.error = "Por favor ingrese un correo electrónico válido"
                }
                UserValidEmail -> {
                    binding.edtEmail.error = null
                }
                UserLoggedIn -> {
                    binding.edtEmail.error = null
                }
            }
        }
    }

    private fun validateEmail(): Boolean {
        val email = binding.edtEmail.editText?.text.toString()
        return if (email.isEmpty()) {
            binding.edtEmail.error = "Correo electrónico requerido"
            false
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = "Por favor ingrese un correo electrónico válido"
            false
        } else {
            binding.edtEmail.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        val password = binding.edtPassword.editText?.text.toString()

        // Expresiones regulares requeridas para validar la contraseña
        val passwordRegex = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         //al menos 1 dígito
                    "(?=.*[a-z])" +         //al menos 1 letra minúscula
                    "(?=.*[A-Z])" +         //al menos 1 letra mayúscula
                    "(?=.*[@#$%^&+=])" +    //al menos 1 carácter especial
                    "(?=\\S+$)" +           //sin espacios en blanco
                    ".{5,}" +               //al menos 4 caracteres
                    "$"
        )
        // Password: longitud minima de 5 caracteres

        return if (password.isEmpty()) {
            binding.edtPassword.error = "Contraseña requerida"
            false
        } else if (!passwordRegex.matcher(password).matches()) {
            binding.edtPassword.error = "La contraseña es demasiado corta"
            false
        } else {
            binding.edtPassword.error = null
            true
        }
    }

    private fun validate() {
        val result = arrayOf(validateEmail(), validatePassword())

        if (false in result) {
            return
        }
        val intentHome = Intent(this, HomeActivity::class.java)
        startActivity(intentHome)

        Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_LONG).show()
    }
}

