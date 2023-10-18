package com.example.libarary.domain.book.infra

import com.example.libarary.domain.book.domain.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long> {

  fun findByName(bookName: String): Book?

}