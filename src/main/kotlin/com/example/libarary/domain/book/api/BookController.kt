package com.example.libarary.domain.book.api

import com.example.libarary.domain.book.domain.BookService
import com.example.libarary.domain.book.dto.request.BookLoanRequest
import com.example.libarary.domain.book.dto.request.BookRequest
import com.example.libarary.domain.book.dto.request.BookReturnRequest
import com.example.libarary.global.response.CommonResponse
import org.springframework.web.bind.annotation.*

@RestController
class BookController(
  private val bookService: BookService,
) {

  @PostMapping("/book")
  fun saveBook(
    @RequestBody request: BookRequest
  ): CommonResponse<Unit?> {
    bookService.saveBook(request)
    return CommonResponse.success(null, "책 등록 성공")
  }

  @PostMapping("/book/loan")
  fun loanBook(
    @RequestBody request: BookLoanRequest
  ): CommonResponse<Unit?> {
    bookService.loanBook(request)
    return CommonResponse.success(null, "책 대출 성공")
  }

  @PutMapping("/book/return")
  fun returnBook(
    @RequestBody request: BookReturnRequest
  ): CommonResponse<Unit?> {
    bookService.returnBook(request)
    return CommonResponse.success(null, "책 반납 성공")
  }
}