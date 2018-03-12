package com.ut.demo.repositories

import com.ut.demo.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String?): UserEntity?
}