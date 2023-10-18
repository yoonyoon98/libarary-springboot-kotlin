package com.example.libarary.domain.user.dto.response

import com.example.libarary.domain.user.domain.User

data class UserResponse(
  val id: Long,
  val name: String,
  val age: Int?,
) {

  companion object {
    fun of(user: User): UserResponse {
      return UserResponse(
        id = user.id!!,
        name = user.name,
        age = user.age
      )
    }
  }

}