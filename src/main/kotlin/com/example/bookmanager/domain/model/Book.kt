package com.example.bookmanager.domain.model

data class Book(
    val title: String,
    val author: String
) {
    init {
        require(title.isNotBlank()) { "Le titre ne peut pas être vide" }
        require(author.isNotBlank()) { "L'auteur ne peut pas être vide" }
    }
}