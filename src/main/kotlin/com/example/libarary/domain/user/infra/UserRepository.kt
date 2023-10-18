package com.example.libarary.domain.user.infra

import com.example.libarary.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
  fun findByName(name: String): User?
}