package com.example.libarary.domain.user.domain

import com.example.libarary.domain.book.domain.Book
import com.example.libarary.domain.loanhistory.domain.UserLoanHistory
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User (
    var name: String,
    val age: Int,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    companion object {
        private const val NAME_REGEX = "^[가-힣a-zA-Z]{2,10}$"

        fun create(name: String, age: Int): User {
            validateAge(age)
            validateName(name)
            return User(name, age)
        }

        private fun validateName(name: String) {
            require(name.isNotBlank()) { "이름은 필수입니다." }
            require(name.length in 2..10) { "이름은 2~10자리여야 합니다." }
            require(name.matches(Regex(NAME_REGEX))) { "이름은 한글, 영문으로만 구성되어야 합니다." }
        }

        private fun validateAge(age: Int) {
            require(age > 0) { "나이는 0보다 작을 수 없습니다." }
        }
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun loanBook(book: Book) {
        this.userLoanHistories.add(UserLoanHistory(this, book.name))
    }

    fun returnBook(bookName: String) {
        this.userLoanHistories.first { history -> history.bookName == bookName }.doReturn()
    }
}