package com.ariadna.taskieapp.templatemvvm.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ariadna.taskieapp.R
import com.ariadna.taskieapp.databinding.ActivityHomeBinding
import com.ariadna.taskieapp.templatemvvm.data.local.PrefsUser
import com.ariadna.taskieapp.templatemvvm.data.repository.TaskieRepository
import com.ariadna.taskieapp.templatemvvm.ui.home.viewModel.HomeViewModel
import com.ariadna.taskieapp.templatemvvm.ui.home.viewModel.HomeViewModelFactory


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel:HomeViewModel by lazy {
        val factory = HomeViewModelFactory(TaskieRepository(PrefsUser()))
        ViewModelProvider(this@HomeActivity,factory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        initUI()

        binding.buttonExit.setOnClickListener{
            viewModel.deleteData()
            onBackPressed()
        }
    }

    private fun initUI(){
        Toast.makeText(this, "Bienvenido ${viewModel.getEmail()}", Toast.LENGTH_LONG).show()
        if (viewModel.validateIsUserLoggedIn()){
            Toast.makeText(this,"Sesión guardada", Toast.LENGTH_LONG).show()
        }
    }

}