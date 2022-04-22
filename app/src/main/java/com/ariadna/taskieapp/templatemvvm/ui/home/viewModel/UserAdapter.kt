package com.ariadna.taskieapp.templatemvvm.ui.home.viewModel

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ariadna.taskieapp.R.*
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
                    binding.container.setCardBackgroundColor(ContextCompat.getColor(itemView.context, color.dark_indigo))
                }
                PriorityColor.MEDIUM -> {
                    binding.container.setCardBackgroundColor(ContextCompat.getColor(itemView.context, color.p_dark_blue))
                }
                PriorityColor.LOW -> {
                    binding.container.setCardBackgroundColor(ContextCompat.getColor(itemView.context, color.primary_blue))
                }
            }

            binding.moreOptions.setOnClickListener {
                popupMenu(it)
            }
        }
        private fun popupMenu(view: View) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.inflate(menu.show_options)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    id.deleteNote->{
                        AlertDialog.Builder(binding.root.context)
                            .setTitle("Eliminar nota")
                            .setIcon(drawable.ic_baseline_warning)
                            .setMessage("¿Estás seguro(a) de querer eliminar esta nota?")
                            .setPositiveButton("Sí")  {
                                    dialog,_->
                                    deleteNote(position = 0)
                                    Toast.makeText(binding.root.context,"Nota eliminada", Toast.LENGTH_SHORT).show()
                                    Log.e("task message","nota borrada")
                                    dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else -> {true}
                }
            }
            popupMenu.show()
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

    fun deleteNote (position: Int){
        noteList.removeAt(position)
        notifyItemRemoved(position)
        Log.e("adapter", "cantidad elementos ${noteList.size}")
    }
}