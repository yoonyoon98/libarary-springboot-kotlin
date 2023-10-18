package com.example.libarary.domain.user.dto.response

import com.example.libarary.domain.loanhistory.domain.UserLoanHistory
import com.example.libarary.domain.user.domain.User

data class UserLoanHistoryResponse(
  val name: String, // 유저 이름
  val books: List<BookHistoryResponse>
) {
  companion object {
    fun of(user: User): UserLoanHistoryResponse {
      return UserLoanHistoryResponse(
        name = user.name,
        books = user.userLoanHistories.map(BookHistoryResponse.Companion::of)
      )
    }
  }
}

data class BookHistoryResponse(
  val name: String, // 책의 이름
  val isReturn: Boolean,
) {
  companion object {
    fun of(history: UserLoanHistory): BookHistoryResponse {
      return BookHistoryResponse(
        name = history.bookName,
        isReturn = history.isReturn
      )
    }
  }
}
