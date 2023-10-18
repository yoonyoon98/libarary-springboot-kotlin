package com.example.libarary.domain.book.domain

import com.example.libarary.domain.book.dto.request.BookLoanRequest
import com.example.libarary.domain.book.dto.request.BookRequest
import com.example.libarary.domain.book.dto.request.BookReturnRequest
import com.example.libarary.domain.book.infra.BookRepository
import com.example.libarary.domain.loanhistory.domain.UserLoanStatus
import com.example.libarary.domain.loanhistory.infra.UserLoanHistoryRepository
import com.example.libarary.domain.user.infra.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BookService(
  private val bookRepository: BookRepository,
  private val userRepository: UserRepository,
  private val userLoanHistoryRepository: UserLoanHistoryRepository
) {

  @Transactional
  fun saveBook(request: BookRequest) {
    bookRepository.save(
      Book.create(
        request.name,
        request.type
      )
    )
  }

  @Transactional
  fun loanBook(request: BookLoanRequest) {
    val book = bookRepository.findByName(request.bookName) ?: throw IllegalArgumentException()
    if(userLoanHistoryRepository.findByBookNameAndStatus(request.bookName, UserLoanStatus.LOANED) != null) {
      throw IllegalArgumentException("이미 대출되어 있는 책입니다.")
    }

    val user = userRepository.findByName(request.userName) ?: throw IllegalArgumentException()
    user.loanBook(book)
  }

  @Transactional
  fun returnBook(request: BookReturnRequest) {
    val user = userRepository.findByName(request.userName) ?: throw IllegalArgumentException()
    user.returnBook(request.bookName)
  }
}