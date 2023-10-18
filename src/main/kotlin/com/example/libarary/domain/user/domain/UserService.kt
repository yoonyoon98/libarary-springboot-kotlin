package com.example.libarary.domain.user.domain

import com.example.libarary.domain.user.dto.request.UserCreateRequest
import com.example.libarary.domain.user.dto.request.UserUpdateRequest
import com.example.libarary.domain.user.dto.response.UserLoanHistoryResponse
import com.example.libarary.domain.user.dto.response.UserResponse
import com.example.libarary.domain.user.infra.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
  private val userRepository: UserRepository,
) {

  @Transactional
  fun saveUser(request: UserCreateRequest) {
    userRepository.save(
      User.create(
        request.name,
        request.age
      )
    )
  }

  fun getUsers(): List<UserResponse> {
    return userRepository.findAll()
      .map { user -> UserResponse.of(user) }
  }

  @Transactional
  fun updateUserName(request: UserUpdateRequest) {
    val user = userRepository.findByIdOrNull(request.id) ?: throw IllegalArgumentException()
    user.updateName(request.name)
  }

  @Transactional
  fun deleteUser(name: String) {
    val user = userRepository.findByName(name) ?: throw IllegalArgumentException()
    userRepository.delete(user)
  }
}