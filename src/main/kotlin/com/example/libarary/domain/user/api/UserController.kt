package com.example.libarary.domain.user.api

import com.example.libarary.domain.user.domain.UserService
import com.example.libarary.domain.user.dto.request.UserCreateRequest
import com.example.libarary.domain.user.dto.request.UserUpdateRequest
import com.example.libarary.domain.user.dto.response.UserLoanHistoryResponse
import com.example.libarary.domain.user.dto.response.UserResponse
import com.example.libarary.global.response.CommonResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
  private val userService: UserService,
) {

  @PostMapping
  fun saveUser(
    @RequestBody request: UserCreateRequest
  ): CommonResponse<Unit?> {
    userService.saveUser(request)
    return CommonResponse.success(null, "회원 저장 성공")
  }

  @GetMapping
  fun getUsers(): CommonResponse<List<UserResponse>> {
    return CommonResponse.success(userService.getUsers(), "회원 조회 성공")
  }

  @PutMapping
  fun updateUserName(
    @RequestBody request: UserUpdateRequest
  ): CommonResponse<Unit?> {
    userService.updateUserName(request)
    return CommonResponse.success(null, "회원 이름 수정 성공")
  }

  @DeleteMapping
  fun deleteUser(
    @RequestParam name: String
  ): CommonResponse<Unit?> {
    userService.deleteUser(name)
    return CommonResponse.success(null, "회원 삭제 성공")
  }
}