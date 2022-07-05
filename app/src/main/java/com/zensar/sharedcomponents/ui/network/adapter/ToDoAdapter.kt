package com.zensar.sharedcomponents.ui.network.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zensar.sharedcomponents.ui.network.model.Todos
import com.zensar.sharedcomponents.ui.network.model.TodosItem
import com.zensar.sharedcomponents.databinding.LayoutRowItemBinding

class ToDoAdapter(private val todos: Todos): RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutRowItemBinding.inflate(inflater, parent, false)
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bindTodo(todos[position])
    }

    override fun getItemCount() = todos.size

    class ToDoViewHolder(private val binding: LayoutRowItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bindTodo(todoItem: TodosItem) {

            with(binding) {
                tvTitle.text = todoItem.title
            }
        }
    }
}