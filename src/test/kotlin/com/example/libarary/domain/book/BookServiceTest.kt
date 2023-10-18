package com.example.libarary.domain.book

import com.example.libarary.domain.book.domain.Book
import com.example.libarary.domain.book.domain.BookService
import com.example.libarary.domain.book.domain.BookType
import com.example.libarary.domain.book.dto.request.BookLoanRequest
import com.example.libarary.domain.book.dto.request.BookRequest
import com.example.libarary.domain.book.dto.request.BookReturnRequest
import com.example.libarary.domain.book.infra.BookRepository
import com.example.libarary.domain.loanhistory.domain.UserLoanHistory
import com.example.libarary.domain.loanhistory.domain.UserLoanStatus
import com.example.libarary.domain.loanhistory.infra.UserLoanHistoryRepository
import com.example.libarary.domain.user.domain.User
import com.example.libarary.domain.user.infra.UserRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
  private val bookService: BookService,
  private val bookRepository: BookRepository,
  private val userRepository: UserRepository,
  private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

  @AfterEach
  fun clean() {
    bookRepository.deleteAll()
    userRepository.deleteAll()
  }

  @Test
  @DisplayName("책 등록이 정상 동작한다")
  fun saveBookTest() {
    // given
    val request = BookRequest("이상한 나라의 엘리스", BookType.COMPUTER)

    // when
    bookService.saveBook(request)

    // then
    val books = bookRepository.findAll()
    assertThat(books).hasSize(1)
    assertThat(books[0].name).isEqualTo("이상한 나라의 엘리스")
    assertThat(books[0].type).isEqualTo(BookType.COMPUTER)
  }

  @Test
  @DisplayName("책 대출이 정상 동작한다")
  fun loanBookTest() {
    // given
    val savedBook = bookRepository.save(Book.fixture("이상한 나라의 엘리스"))
    val savedUser = userRepository.save(User("A", 21))
    val request = BookLoanRequest("A", "이상한 나라의 엘리스")

    // when
    bookService.loanBook(request)

    // then
    val results = userLoanHistoryRepository.findAll()
    assertThat(results).hasSize(1)
    assertThat(results[0].bookName).isEqualTo("이상한 나라의 엘리스")
    assertThat(results[0].user.id).isEqualTo(savedUser.id)
    assertThat(results[0].status).isEqualTo(UserLoanStatus.LOANED)
  }

  @Test
  @DisplayName("책이 진작 대출되어 있다면, 신규 대출이 실패한다")
  fun loanBookFailTest() {
    // given
    val book = bookRepository.save(Book.fixture("이상한 나라의 엘리스"))
    val savedUser = userRepository.save(User("A", 21))
    userLoanHistoryRepository.save(UserLoanHistory.fixture(savedUser, book.name))
    val request = BookLoanRequest("B", book.name)

    // when & then
    val message = assertThrows<IllegalArgumentException> {
      bookService.loanBook(request)
    }.message
    assertThat(message).isEqualTo("이미 대출되어 있는 책입니다.")
  }

  @Test
  @DisplayName("책 반납이 정상 동작한다")
  fun returnBookTest() {
    // given
  bookRepository.save(Book.fixture("이상한 나라의 엘리스"))
    val savedUser = userRepository.save(User("A", 21))
    userLoanHistoryRepository.save(UserLoanHistory.fixture(savedUser, "이상한 나라의 엘리스"))
    val request = BookReturnRequest("A", "이상한 나라의 엘리스")

    // when
    bookService.returnBook(request)

    // then
    val results = userLoanHistoryRepository.findAll()
    assertThat(results).hasSize(1)
    assertThat(results[0].status).isEqualTo(UserLoanStatus.RETURNED)
  }
}