package com.ariadna.taskieapp.templatemvvm.ui.home.viewModel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ariadna.taskieapp.R
import com.ariadna.taskieapp.templatemvvm.data.repository.data.UserDataNotes

class UserAdapter(val context: Context, private val noteList: ArrayList<UserDataNotes> ): RecyclerView.Adapter<UserAdapter.UserViewHolder>()
{

    inner class UserViewHolder(v: View):RecyclerView.ViewHolder(v){
        val title: TextView = v.findViewById (R.id.noteTitle)
        val description: TextView = v.findViewById (R.id.noteDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item,parent,false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val  newList = noteList[position]
        holder.title.text = newList.titleNote
        holder.description.text = newList.noteContent
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}