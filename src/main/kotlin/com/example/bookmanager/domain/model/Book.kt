package com.example.bookmanager.domain.model

data class Book(
    val title: String,
    val author: String
) {
    init {
        require(title.isNotBlank()) { "Title cannot be empty" }
        require(author.isNotBlank()) { "Author cannot be empty" }
    }
}