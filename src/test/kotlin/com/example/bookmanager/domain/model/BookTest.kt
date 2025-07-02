package com.example.bookmanager.domain.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec

class BookTest : StringSpec({
    "Title cannot be empty" {
        shouldThrow<IllegalArgumentException> { Book("", "Author") }

    }

    "Author cannot be empty" {
        shouldThrow<IllegalArgumentException> { Book("Title", "") }
    }
})