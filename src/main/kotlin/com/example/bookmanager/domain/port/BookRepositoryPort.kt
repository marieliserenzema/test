package com.example.bookmanager.domain.port

import com.example.bookmanager.domain.model.Book

interface BookRepositoryPort {
    fun save(book: Book)
    fun findAll(): List<Book>
}