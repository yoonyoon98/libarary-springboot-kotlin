package com.example.libarary.domain.book.dto.request

import com.example.libarary.domain.book.domain.BookType

data class BookRequest(
  val name: String,
  val type: BookType,
)
