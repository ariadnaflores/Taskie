package com.ariadna.taskieapp.templatemvvm.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ariadna.taskieapp.R
import com.ariadna.taskieapp.databinding.ActivityLoginBinding
import com.ariadna.taskieapp.templatemvvm.data.local.PrefsUser
import com.ariadna.taskieapp.templatemvvm.data.remote.FirebaseAuthManager
import com.ariadna.taskieapp.templatemvvm.data.repository.login.LoginRepository
import com.ariadna.taskieapp.templatemvvm.ui.home.HomeActivity
import com.ariadna.taskieapp.templatemvvm.ui.login.viewModel.*
import com.ariadna.taskieapp.templatemvvm.ui.utils.FormValidations


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val firebaseManager: FirebaseAuthManager = FirebaseAuthManager()

    private val viewModel: LoginViewModel by lazy {
        val factory = LoginViewModelFactory(
            FormValidations(),
            LoginRepository(firebaseManager, PrefsUser())
        )
        ViewModelProvider(this@LoginActivity, factory)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.buttonBack.setOnClickListener {
            finish()
        }
        initUI()
    }

    private fun initUI() {
        binding.buttonAccountLogin.setOnClickListener {
            subscribeObservers()
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
                    binding.edtEmail.error = getString(R.string.form_error_empty_email)
                }
                UserInvalidEmail -> {
                    binding.edtEmail.error = getString(R.string.form_error_invalid_email)
                }
                UserValidEmail -> {
                    binding.edtEmail.error = null
                }
                UserLoggedIn -> {
                    binding.edtEmail.error = null
                    binding.edtPassword.error = null
                    viewModel.signIn(userEmail = binding.textInputEditTextEmail.text.toString(),
                        userPassword = binding.textInputEditTextPassword.text.toString(),
                        activity = this)
                }
                UserEmptyPassword -> {
                    binding.edtPassword.error = getString(R.string.form_error_empty_password)
                }
                UserInvalidPasswordLength -> {
                    binding.edtPassword.error = getString(R.string.form_error_password_length)
                }
                UserValidPassword -> {
                    binding.edtPassword.error = null
                }
                FirebaseSuccessful -> {
                    val intentRegister = Intent(this, HomeActivity::class.java)
                    startActivity(intentRegister)
                    Toast.makeText(this,getString(R.string.user_successful_login), Toast.LENGTH_LONG).show()
                }
                FirebaseFailed ->{
                    Toast.makeText(this,getString(R.string.user_failed_login), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

