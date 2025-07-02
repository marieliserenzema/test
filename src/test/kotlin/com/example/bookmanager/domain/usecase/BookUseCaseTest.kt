package com.example.bookmanager.domain.usecase

import com.example.bookmanager.domain.model.Book
import com.example.bookmanager.domain.port.BookRepositoryPort
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.*

class BookUseCaseTest : StringSpec({
    val bookRepository = mockk<BookRepositoryPort>()
    val useCase = BookUseCase(bookRepository)



    "getBooks returns sorted list of books" {
        // Arrange
        val books = listOf(
            Book("Les robots", "Isaac Asimov"),
            Book("Hypérion", "Dan Simons"),
            Book("Axiomatique", "Greg Egan")
        )
        every { bookRepository.findAll() } returns books

        // Act
        val result = useCase.listBooks()

        // Assert
        result shouldContainExactly listOf(
            Book("Axiomatique", "Greg Egan"),
            Book("Hypérion", "Dan Simons"),
            Book("Les robots", "Isaac Asimov")
        )
    }

    "addBook adds a book to the repository" {
        // Arrange
        val book = Book("Les robots", "Isaac Asimov")
        justRun { bookRepository.save(any()) }

        // Act
        useCase.addBook(book.title, book.author)

        // Assert
        verify(exactly = 1) { bookRepository.save(book) }
    }})
