package com.example.libarary.domain.loanhistory.infra

import com.example.libarary.domain.loanhistory.domain.UserLoanHistory
import com.example.libarary.domain.loanhistory.domain.UserLoanStatus
import org.springframework.data.jpa.repository.JpaRepository

interface UserLoanHistoryRepository : JpaRepository<UserLoanHistory, Long> {
    fun findByBookNameAndStatus(bookName: String, status: UserLoanStatus): UserLoanHistory?
}