package com.example.bookmanager.domain.usecase

import com.example.bookmanager.domain.model.Book
import com.example.bookmanager.domain.port.BookRepositoryPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class BookUseCaseTest : FunSpec({
    val repository = mockk<BookRepositoryPort>(relaxed = true)
    val useCase = BookUseCase(repository)

    context("addBook") {
        test("empty title throws with appropriate message"){
            shouldThrow<IllegalArgumentException> {
               useCase.addBook("", "Auteur")
           }.message shouldBe "Title cannot be empty"
            verify(exactly = 0) { repository.save(any()) }
        }

        test("empty author throws with appropriate message"){
            shouldThrow<IllegalArgumentException> {
                useCase.addBook("Titre", "")
            }.message shouldBe "Author cannot be empty"
            verify(exactly = 0) { repository.save(any()) }
        }

        test("adding a valid book calls save on the repository") {
            useCase.addBook("1984", "George Orwell")
            verify { repository.save(Book("1984", "George Orwell")) }
        }

    }

    context("listBooks"){
        test("calls findAll() and returns the books sorted by title") {
            val unsorted = listOf(
                Book("Zorro", "A"),
                Book("alpha", "B"),
                Book("Mike", "C")
            )
            every { repository.findAll() } returns unsorted

            val result = useCase.listBooks()

            verify { repository.findAll() }
            result.map { it.title } shouldBe listOf("alpha", "Mike", "Zorro")
        }

    }
})
