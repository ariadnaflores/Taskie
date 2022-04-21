package com.ariadna.taskieapp.templatemvvm.ui.home.viewModel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ariadna.taskieapp.R
import com.ariadna.taskieapp.databinding.ListItemBinding
import com.ariadna.taskieapp.templatemvvm.data.repository.data.PriorityColor
import com.ariadna.taskieapp.templatemvvm.data.repository.data.UserDataNotes

class UserAdapter(private val noteList: ArrayList<UserDataNotes> ): RecyclerView.Adapter<UserAdapter.UserViewHolder>()
{

    inner class UserViewHolder(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bindNotes(userDataNotes: UserDataNotes){
            binding.noteTitle.text = userDataNotes.titleNote
            binding.noteDescription.text = userDataNotes.noteContent

            when(userDataNotes.priorityColor){
                PriorityColor.HIGH -> {
                    binding.container.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.dark_indigo))
                }
                PriorityColor.MEDIUM -> {
                    binding.container.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.p_dark_blue))
                }
                PriorityColor.LOW -> {
                    binding.container.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.primary_blue))
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(ListItemBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val  addNoteList = noteList[position]
        holder.bindNotes(addNoteList)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNote (
        title : String,
        content : String,
        priorityColor: PriorityColor
    ) {
        noteList.add(UserDataNotes("Título : $title", "Contenido $content", priorityColor = priorityColor))
        notifyDataSetChanged()
    }

}