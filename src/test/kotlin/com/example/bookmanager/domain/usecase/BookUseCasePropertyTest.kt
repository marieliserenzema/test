package com.example.bookmanager.domain.usecase

import com.example.bookmanager.domain.model.Book
import com.example.bookmanager.domain.port.BookRepositoryPort
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.property.Arb
import io.kotest.property.arbitrary.stringPattern
import io.kotest.property.checkAll

class InMemoryBookRepositoryPort : BookRepositoryPort {
    private val books = mutableListOf<Book>()

    override fun findAll(): List<Book> = books

    override fun save(book: Book) {
        books.add(book)
    }

    fun clear() {
        books.clear()
    }
}

class BookUseCasePropertyTest : StringSpec({

    val bookRepository = InMemoryBookRepositoryPort()
    val libraryUseCase = BookUseCase(bookRepository)

    "should return all elements in the alphabetical order" {
        bookRepository.clear()
        val titles = mutableListOf<String>()
        checkAll(Arb.stringPattern("""[A-Za-z]{1,10}""")) { title ->
            titles.add(title)
            libraryUseCase.addBook(title, "Isaac Asimov")
        }

        val resultTitles = libraryUseCase.listBooks().map { it.title }
        resultTitles shouldContainExactly titles
            .sortedWith(compareBy(String::lowercase))

    }
})