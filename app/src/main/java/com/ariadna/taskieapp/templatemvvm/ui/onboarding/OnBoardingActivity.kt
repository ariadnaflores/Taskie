package com.ariadna.taskieapp.templatemvvm.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ariadna.taskieapp.R
import com.ariadna.taskieapp.databinding.ActivityOnboardingBinding
import com.ariadna.taskieapp.templatemvvm.ui.login.LoginActivity
import com.ariadna.taskieapp.templatemvvm.ui.register.RegisterActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)

        binding.textButtonLogin.setOnClickListener{
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }

        binding.buttonRegister.setOnClickListener{
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }

    }
}