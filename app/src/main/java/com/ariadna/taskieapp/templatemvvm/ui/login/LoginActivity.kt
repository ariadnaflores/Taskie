package com.ariadna.taskieapp.templatemvvm.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.databinding.DataBindingUtil
import com.ariadna.taskieapp.R
import com.ariadna.taskieapp.databinding.ActivityLoginBinding
import com.ariadna.taskieapp.templatemvvm.ui.home.HomeActivity
import com.ariadna.taskieapp.templatemvvm.ui.onboarding.OnBoardingActivity
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.buttonBack.setOnClickListener{
            val intentBackOnBoarding = Intent(this, OnBoardingActivity::class.java)
            startActivity(intentBackOnBoarding)
        }

        fun validateEmail(): Boolean {
            val email = binding.edtEmail.editText?.text.toString()
            return  if (email.isEmpty()){
                binding.edtEmail.error = "Correo electrónico requerido"
                false
            }else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmail.error = "Por favor ingrese un correo electrónico válido"
                false
            } else {
                binding.edtEmail.error = null
                true
            }
        }

        fun validatePassword(): Boolean {
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

            return  if (password.isEmpty()){
                binding.edtPassword.error = "Contraseña requerida"
                false
            }else if (!passwordRegex.matcher(password).matches()) {
                binding.edtPassword.error = "La contraseña es demasiado corta"
                false
            } else {
                binding.edtPassword.error = null
                true
            }
        }

        fun validate() {
            val result = arrayOf(validateEmail(), validatePassword())

            if (false in result){
                return
            }
            val intentHome = Intent(this, HomeActivity::class.java)
            startActivity(intentHome)

            Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_LONG).show()
        }

        binding.buttonAccountLogin.setOnClickListener {

            validate()

        }


    }
}

