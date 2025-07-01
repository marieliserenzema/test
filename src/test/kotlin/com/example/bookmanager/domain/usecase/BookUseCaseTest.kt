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
        test("ajout d'un livre sans titre lance une exception"){
            shouldThrow<IllegalArgumentException> {
               useCase.addBook("", "Auteur")
           }.message shouldBe "Le titre ne peut pas être vide"
            verify(exactly = 0) { repository.save(any()) }
        }

        test("ajout d'un livre sans auteur lance une exception"){
            shouldThrow<IllegalArgumentException> {
                useCase.addBook("Titre", "")
            }.message shouldBe "L'auteur ne peut pas être vide"
            verify(exactly = 0) { repository.save(any()) }
        }

        test("ajout d'un livre valide appelle le repository") {
            useCase.addBook("1984", "George Orwell")
            verify { repository.save(Book("1984", "George Orwell")) }
        }

    }

    context("listBooks"){
        test("appelle findAll() et retourne les livres triés par titre (insensible à la casse)") {
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
