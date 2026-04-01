package com.dierlisson.kanbantaskapp.model

data class Task(
    val id: String = "",
    val title: String,
    val description: String,
    val status: String
)