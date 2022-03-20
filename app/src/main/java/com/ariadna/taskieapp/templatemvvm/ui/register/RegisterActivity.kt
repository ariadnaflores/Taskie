package com.ariadna.taskieapp.templatemvvm.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ariadna.taskieapp.R
import com.ariadna.taskieapp.databinding.ActivityRegisterBinding
import com.ariadna.taskieapp.templatemvvm.data.local.PrefsUser
import com.ariadna.taskieapp.templatemvvm.data.remote.FirebaseAuthManager
import com.ariadna.taskieapp.templatemvvm.data.repository.register.RegisterRepository
import com.ariadna.taskieapp.templatemvvm.ui.login.LoginActivity
import com.ariadna.taskieapp.templatemvvm.ui.register.viewmodel.*
import com.ariadna.taskieapp.templatemvvm.ui.utils.FormValidations

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val firebaseManager: FirebaseAuthManager = FirebaseAuthManager()

    private val viewModel: RegisterViewModel by lazy {
        val factory = RegisterViewModelFactory( FormValidations(),
            RegisterRepository(firebaseManager, PrefsUser()))
            ViewModelProvider(this@RegisterActivity, factory)[RegisterViewModel::class.java]
        }

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
        initUI()
    }

    private fun initUI(){
        binding.buttonAccountRegister.setOnClickListener{
            subscribeObservers()
            viewModel.checkFields(
                userEmail = binding.edtEmail.editText?.text.toString(),
                userPassword = binding.edtPassword.editText?.text.toString()
            )
        }
    }

    private fun subscribeObservers() {
        viewModel.registerViewStateLiveData.observe(this) {
            when (it) {
                UserEmptyEmail-> {
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
                    viewModel.createUser(userEmail = binding.textInputEditTextEmail.text.toString(),
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
                UserSuccessfullyCreatedInFirebase -> {
                    Toast.makeText(this,getString(R.string.user_successfully_created), Toast.LENGTH_LONG).show()
                    Toast.makeText(this,getString(R.string.email_sent), Toast.LENGTH_LONG).show()
                    val intentRegister = Intent(this, LoginActivity::class.java)
                    startActivity(intentRegister)
                    finish()
                }
                UnsuccessfulUserInFirebase -> {
                    Toast.makeText(this,getString(R.string.unsuccessful_user_created), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}