package com.example.libarary.domain.book.dto.request

data class BookLoanRequest(
  val userName: String,
  val bookName: String
)