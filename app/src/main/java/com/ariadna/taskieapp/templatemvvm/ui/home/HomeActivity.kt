package com.ariadna.taskieapp.templatemvvm.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariadna.taskieapp.R
import com.ariadna.taskieapp.databinding.ActivityHomeBinding
import com.ariadna.taskieapp.databinding.AddItemBinding
import com.ariadna.taskieapp.templatemvvm.data.local.PrefsUser
import com.ariadna.taskieapp.templatemvvm.data.remote.FirebaseAuthManager
import com.ariadna.taskieapp.templatemvvm.data.repository.TaskieRepository
import com.ariadna.taskieapp.templatemvvm.data.repository.data.UserDataNotes
import com.ariadna.taskieapp.templatemvvm.data.repository.home.HomeRepository
import com.ariadna.taskieapp.templatemvvm.ui.home.viewModel.HomeViewModel
import com.ariadna.taskieapp.templatemvvm.ui.home.viewModel.HomeViewModelFactory
import com.ariadna.taskieapp.templatemvvm.ui.home.viewModel.UserAdapter
import com.ariadna.taskieapp.templatemvvm.ui.home.viewModel.UserLogOut
import com.ariadna.taskieapp.templatemvvm.ui.onboarding.OnBoardingActivity
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var noteList: ArrayList<UserDataNotes>
    private lateinit var userAdapter: UserAdapter

    private val viewModel:HomeViewModel by lazy {
        val factory = HomeViewModelFactory(
            TaskieRepository(PrefsUser()),
            HomeRepository(firebaseAuthManager = FirebaseAuthManager(), prefsUser = PrefsUser())
        )
        ViewModelProvider(this@HomeActivity,factory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        initUI()
        subscribeObservers()

        /*binding.buttonExit.setOnClickListener {
            viewModel.logOut()
        }*/

        noteList = ArrayList()
        userAdapter = UserAdapter(this,noteList)
        listRecyler.layoutManager = LinearLayoutManager(this)
        listRecyler.adapter = userAdapter
        addingButton.setOnClickListener{
            addInfo()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addInfo() {
        val inflater = LayoutInflater.from(this)
        inflater.inflate(R.layout.add_item,null)
        val dialogBinding = AddItemBinding.inflate(LayoutInflater.from(this),binding.container, false)
        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(dialogBinding.root)
        addDialog.setPositiveButton("Ok"){
            dialog,_->
            val title = dialogBinding.titleNoteInDialog.text.toString()
            val content = dialogBinding.contentNoteInDialog.text.toString()
            noteList.add(UserDataNotes("Titulo : $title", "Contenido $content"))
            userAdapter.notifyDataSetChanged()
            Toast.makeText(this,getString(R.string.message_dialog_ok),Toast.LENGTH_SHORT).show()
            dialog.dismiss()


        }
        addDialog.setNegativeButton("Cancel"){
            dialog,_->
            dialog.dismiss()
            Toast.makeText(this,getString(R.string.message_dialog_cancel),Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }

    private fun initUI(){
        Toast.makeText(this, "Bienvenido ${viewModel.getEmail()}", Toast.LENGTH_SHORT).show()
        if (viewModel.validateIsUserLoggedIn()){
            Toast.makeText(this,getString(R.string.saved_session), Toast.LENGTH_SHORT).show()
        }
    }

    private fun subscribeObservers() {
        viewModel.homeViewStateLiveData.observe(this) {
            when (it) {
                UserLogOut -> {
                    finish()
                    val intentLogOut = Intent(this, OnBoardingActivity::class.java)
                    startActivity(intentLogOut)
                    Toast.makeText(this, getString(R.string.log_out),Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
