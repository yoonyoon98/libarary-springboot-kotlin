package com.example.libarary.domain.book.domain

import jakarta.persistence.*

@Entity
@Table(name = "books")
class Book(
  val name: String,

  @Enumerated(EnumType.STRING)
  val type: BookType,

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
) {

  companion object {
    fun create(name: String, type: BookType): Book {
      validateName(name)
      return Book(name, type)
    }

    private fun validateName(name: String) {
      require(name.isNotBlank()) { "책 이름은 필수입니다." }
      require(name.length in 2..100) { "책 이름은 2~100자리여야 합니다." }
    }


    fun fixture(
      name: String = "책 이름 없음",
      type: BookType = BookType.COMPUTER,
      id: Long? = null,
    ): Book {
      return Book(
        name = name,
        type = type,
        id = id,
      )
    }
  }

}
