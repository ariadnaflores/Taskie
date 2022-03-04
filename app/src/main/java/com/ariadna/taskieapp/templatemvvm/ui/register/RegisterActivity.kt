package com.ariadna.taskieapp.templatemvvm.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ariadna.taskieapp.R
import com.ariadna.taskieapp.databinding.ActivityRegisterBinding
import com.ariadna.taskieapp.templatemvvm.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.textButtonLogin.setOnClickListener{
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }
    }
}