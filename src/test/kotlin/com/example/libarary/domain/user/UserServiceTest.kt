package com.example.libarary.domain.user

import com.example.libarary.domain.loanhistory.infra.UserLoanHistoryRepository
import com.example.libarary.domain.user.domain.User
import com.example.libarary.domain.user.domain.UserService
import com.example.libarary.domain.user.dto.request.UserCreateRequest
import com.example.libarary.domain.user.dto.request.UserUpdateRequest
import com.example.libarary.domain.user.infra.UserRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
  private val userRepository: UserRepository,
  private val userService: UserService,
  private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

  @AfterEach
  fun clean() {
    userRepository.deleteAll()
  }

  @Test
  @DisplayName("유저 저장이 정상 동작한다")
  fun saveUserTest() {
    // given
    val request = UserCreateRequest("윤성현", 10)

    // when
    userService.saveUser(request)

    // then
    val results = userRepository.findAll()
    assertThat(results).hasSize(1)
    assertThat(results[0].name).isEqualTo("윤성현")
    assertThat(results[0].age).isEqualTo(10)
  }

  @Test
  @DisplayName("유저 조회가 정상 동작한다")
  fun getUsersTest() {
    // given
    userRepository.saveAll(listOf(
      User("A", 20),
      User("B", 20),
    ))

    // when
    val results = userService.getUsers()

    // then
    assertThat(results).hasSize(2) // [UserResponse(), UserResponse()]
    assertThat(results).extracting("name").containsExactlyInAnyOrder("A", "B") // ["A", "B"]
    assertThat(results).extracting("age").containsExactlyInAnyOrder(20, 20)
  }

  @Test
  @DisplayName("유저 업데이트가 정상 동작한다")
  fun updateUserNameTest() {
    // given
    val savedUser = userRepository.save(User("A", 21))
    val request = UserUpdateRequest(savedUser.id!!, "B")

    // when
    userService.updateUserName(request)

    // then
    val result = userRepository.findAll()[0]
    assertThat(result.name).isEqualTo("B")
  }

  @Test
  @DisplayName("유저 삭제가 정상 동작한다")
  fun deleteUserTest() {
    // given
    userRepository.save(User("A", 21))

    // when
    userService.deleteUser("A")

    // then
    assertThat(userRepository.findAll()).isEmpty()
  }
}