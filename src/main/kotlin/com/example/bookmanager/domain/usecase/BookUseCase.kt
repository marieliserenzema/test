package com.example.bookmanager.domain.usecase

import com.example.bookmanager.domain.model.Book
import com.example.bookmanager.domain.port.BookRepositoryPort

class BookUseCase(
    private val repository: BookRepositoryPort
) {
    fun addBook(title: String, author: String) {
        val book = Book(title, author)
        repository.save(book)
    }

    fun listBooks(): List<Book> =
        repository.findAll()
            .sortedBy { it.title.lowercase() }
}