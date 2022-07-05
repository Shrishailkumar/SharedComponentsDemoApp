package com.zensar.sharedcomponents.ui.network.model

class Todos : ArrayList<TodosItem>()

data class TodosItem(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)